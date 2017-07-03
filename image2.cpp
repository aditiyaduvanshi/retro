#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <opencv2/core.hpp>
#include <opencv2/ximgproc.hpp>
 
using namespace std;
using namespace cv;
 
int main( int argc, char** argv )
 {
   
     Mat src = imread( "img3.jpg", 0 );
     Mat src1 = imread( "img4.jpg", 0 );
     Mat dst;
 
     //Apply bilateral filter
     //bilateralFilter ( src, dst, 15, 80, 80 );
      cv::ximgproc::jointBilateralFilter(src,src1,dst,15,80.0,80.0)	;	

     //imshow("source", src);
     imshow("result1", dst);  
	
     imwrite( "result1.jpg", dst );
 
     waitKey(0);
     return 0;


    return 0;
 }
