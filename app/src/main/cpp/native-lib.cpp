#include <jni.h>
#include <string>
#include <opencv2/imgproc.hpp>
#include <opencv2/core.hpp>
#include <opencv2/core/mat.hpp>

void Sharpen(cv::Mat &mat, cv::Mat &mat1);

extern "C"
JNIEXPORT void JNICALL
Java_com_martins_cubeit_CameraActivity_processMat(JNIEnv *env, jclass clazz, jlong input_mat) {
    cv::Mat& frame = *(cv::Mat*) input_mat;
    cv::rotate(frame, frame, cv::ROTATE_90_CLOCKWISE);

    cv::Vec3b bgrPixel(40, 158, 16);
    cv::Mat3b bgr (bgrPixel);
    cv::Mat3b hsv;

    cvtColor(bgr, hsv, cv::COLOR_BGR2HSV);
    cvtColor(frame, frame, cv::COLOR_BGR2HSV);

    cv::Vec3b hsvPixel(hsv.at<cv::Vec3b>(0,0));

    int thresh = 40;

    cv::Scalar minHSV = cv::Scalar(hsvPixel.val[0] - thresh, hsvPixel.val[1] - thresh, hsvPixel.val[2] - thresh);
    cv::Scalar maxHSV = cv::Scalar(hsvPixel.val[0] + thresh, hsvPixel.val[1] + thresh, hsvPixel.val[2] + thresh);

    cv::Mat maskHSV, resultHSV;
    cv::inRange(frame, minHSV, maxHSV, maskHSV);
    cv::bitwise_and(frame, frame, resultHSV, maskHSV);

    frame = resultHSV;
}