#pragma version(1)
#pragma rs java_package_name(ece493.imagemanipulation.NonlinearTransoforms)
#define C_PI 3.141592653589793238462643383279502884197169399375

rs_allocation input;
rs_allocation output;

int32_t width;
int32_t height;

//a convenience method to clamp getting pixels into the image
static uchar4 getPixelAt(int x, int y) {
	if(y>=height) y = height-1;
	if(y<0) y = 0;
	if(x>=width) x = width-1;
	if(x<0) x = 0;
	return rsGetElementAt_uchar4(input, x,y);
}

//take care of setting x,y on the 1d-array representing the bitmap
void setPixelAt(int x, int y, uchar4 pixel) {
	rsSetElementAt_uchar4(output, pixel, x, y);
}

void Swirl (double factor) {

    // This function effectively swirls an image
    double cX = (double)width/2.0f;
    double cY = (double)height/2.0f;

    #pragma omp parallel for
    for (int i=0; i < height; i++) {
        float relY = cY-i;
        for (int j=0; j < width; j++) {
            float relX = j-cX;
            // relX and relY are points in our UV space
            // Calculate the angle our points are relative to UV origin. Everything is in radians.
            float originalAngle;
            if (relX != 0) {
                originalAngle = atan(fabs(relY)/fabs(relX));
                if ( relX > 0 && relY < 0) originalAngle = 2.0f*C_PI - originalAngle;
                else if (relX <= 0 && relY >=0) originalAngle = C_PI-originalAngle;
                else if (relX <=0 && relY <0) originalAngle += C_PI;
            }
            else {
                // Take care of rare special case
                if (relY >= 0) originalAngle = 0.5f * C_PI;
                else originalAngle = 1.5f * C_PI;
            }

            // Calculate the distance from the center of the UV using pythagorean distance
            float radius = sqrt(relX*relX + relY*relY);

            // Use any equation we want to determine how much to rotate image by
            //double newAngle = originalAngle + factor*radius; // a progressive twist
            float newAngle = originalAngle + 1/(factor*radius+(4.0f/C_PI));

            // Transform source UV coordinates back into bitmap coordinates
            int srcX = (int)(floor(radius * cos(newAngle)+0.5f));
            int srcY = (int)(floor(radius * sin(newAngle)+0.5f));
            srcX += cX;
            srcY += cY;
            srcY = height - srcY;

            // Clamp the source to legal image pixel
            if (srcX < 0) srcX = 0;
            else if (srcX >= width) srcX = width-1;
            if (srcY < 0) srcY = 0;
            else if (srcY >= height) srcY = height-1;

            // Set the pixel color
            setPixelAt(j, i, getPixelAt(srcX, srcY));
        }
    }
}