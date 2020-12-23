#include <jni.h>
#include <android/log.h>
#include <string>
#include <utility>
#include <vector>
#include <opencv2/imgproc.hpp>
#include <opencv2/core.hpp>
#include <opencv2/core/mat.hpp>
#include <valarray>

#define TAG "ImageProcessing"

struct Cubie {
    int index;
    int determinant;
    double area;
    double aspect;
    cv::Rect boundingRect;
    cv::Point2f center;

    Cubie(int i, int d, double area, double aspect, cv::Rect b, cv::Point2f c) :
    index(i), determinant(d), area(area), aspect(aspect), boundingRect(std::move(b)), center(std::move(c)) {}
};

template<class C, class T>
auto contains(const C& v, const T& x) -> decltype(end(v), true) {
    return end(v) != std::find(begin(v), end(v), x);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_martins_cubeit_CameraActivity_processMat(JNIEnv *env, jclass clazz, jlong frameAddress, jint r1, jint g1, jint b1, jint r2, jint g2, jint b2, jint t) {
    cv::Mat& frame = *(cv::Mat*) frameAddress;
    cv::rotate(frame, frame, cv::ROTATE_90_CLOCKWISE);

    bool isValid = false;

    int frameWidth = frame.size[0];
    int frameHeight = frame.size[1];

    // 95, 110 blue
    // 70, 80  green
    // 25, 32  yellow
    // 12 , 14 orange
//
//    cv::Mat hsvFrame;
//    cv::cvtColor(frame, hsvFrame, cv::COLOR_RGB2HSV);
//
//    cv::Mat bands[3];
//    cv::split(hsvFrame, bands);
//
//    bands[1] = bands[1] * t;
//    std::vector<cv::Mat> channels = {bands[0],bands[1],bands[2]};
//    cv::merge(channels, hsvFrame);

    cv::Mat grayFrame;
//    cv::cvtColor(hsvFrame, hsvFrame, cv::COLOR_HSV2RGB);
    cv::cvtColor(frame, grayFrame, cv::COLOR_RGB2GRAY);

    cv::Mat tresh;
    cv::adaptiveThreshold(grayFrame, tresh, 255, cv::ADAPTIVE_THRESH_MEAN_C, cv::THRESH_BINARY, 21, 4);

    cv::Mat treshRes;
    cv::Mat kernel3 = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(3, 3));
    cv::morphologyEx(tresh, treshRes, cv::MORPH_OPEN, kernel3);

    cv::Mat kernel4 = cv::getStructuringElement(cv::MORPH_ELLIPSE, cv::Size(8, 8));
    cv::erode(treshRes, treshRes, kernel4);

    std::vector<std::vector<cv::Point>> contours;
    std::vector<cv::Vec4i> hierarchy;
    cv::findContours(treshRes, contours, hierarchy, cv::RETR_CCOMP, cv::CHAIN_APPROX_SIMPLE);

    std::vector<Cubie> targets;
    for (int i = 0; i < contours.size(); i++) {
        double area = cv::contourArea(contours[i]);
        cv::Rect boundingRectangle = cv::boundingRect(contours[i]);
        double aspect = (double)boundingRectangle.width / boundingRectangle.height;
        cv::Point2f center = cv::Point2f(boundingRectangle.x + (float)boundingRectangle.width / 2,
                                         boundingRectangle.y + (float)boundingRectangle.height / 2);

        if (area > 2000 && (aspect > 0.9 && aspect < 1.1) && hierarchy[i][3] == -1) {
            targets.emplace_back(i, hierarchy[i][3], area, aspect, boundingRectangle, center);
        }
    }

    if (targets.size() == 9) {
        std::vector<int> processed;
        std::vector<std::vector<Cubie>> rows(3);

        for (auto & row : rows) {
            float firstY = -1;
            for (const Cubie& target : targets) {
                if (firstY == -1 && !contains(processed, target.index)) {
                    firstY = target.center.y;
                }

                if ((firstY + 20.0f > target.center.y && firstY - 20.0f < target.center.y) && !contains(processed, target.index)) {
                    row.push_back(target);
                    processed.push_back(target.index);
                }
            }
        }

        for (auto & row: rows) {
            std::sort(row.begin(), row.end(), [](const Cubie& lhs, const Cubie& rhs) {
                return lhs.center.x < rhs.center.x;
            });
        }

        std::sort(rows.begin(), rows.end(), [](const std::vector<Cubie>& lhs, const std::vector<Cubie>& rhs) {
            float sumLhs = 0;
            float sumRhs = 0;

            for (auto & ls : lhs) sumLhs += ls.center.y;
            for (auto & rs : rhs) sumRhs += rs.center.y;

            return sumLhs / 3 < sumRhs / 3;
        });

        cv::Mat mask = cv::Mat::zeros(treshRes.rows, treshRes.cols, CV_8UC1);
        for (const Cubie& target: targets) {
            cv::drawContours(mask, contours, target.index, cv::Scalar(255, 255, 255), -1);
        }

        frame = mask;

        __android_log_print(ANDROID_LOG_VERBOSE, TAG, "Rows:");
        for (const std::vector<Cubie>& row: rows) {
            for (const Cubie& y: row) {
                __android_log_print(ANDROID_LOG_VERBOSE, TAG,
                                    "i: %d h: %d; %d; %d; %d as: %g area: %g x: %d; y: %d",
                                    y.index, hierarchy[y.index][0], hierarchy[y.index][1],
                                    hierarchy[y.index][2], hierarchy[y.index][3],
                                    y.aspect, y.area, (int) y.center.x, (int) y.center.y);
            }
        }

        isValid = true;
    }

//    for (const Cubie& target : targets) {
//        cv::circle(frame, target.center, 5 + target.index, cv::Scalar(128 + target.index, 50, 20), -1);
//    }

    frame = frame;

    return isValid;
}
