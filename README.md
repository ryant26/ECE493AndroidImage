# ECE493AndroidImage

## Digital Images
* Row major pixel ordering in 1 array
* pixel (i,j) is i pixels blow the top row and j pixels to the right of the left most row
  * p(i,j) = p[i*row + j] 
* 24-bit RGB (0-255)
* last byte used for alpha channel
 
## Image Operations
Pixel ops: Operations on one pixel in isolation
Interpixel ops: Consider the value of a pixel to it's neighboring pixels

### Thresholding (Pixel Op)
Take some limit in the rgb space Eg. (64, 64, 64, 32), if value in each pixel is greater than this threshold we set that pixel to white, if it is less we set it to zero
* (100, 100, 100, 10) -> BLack
* (65, 65, 65, 65) -> white

### Edge Detection (Interpixel Op)
compute the local *gradient*, or use the cauche operator. 

### Filtering: noise reduction (Interpixel op)
* Replace the pixel with the mean of the neighbors
  * Image gets blury
* Replace the pixel with the median of the neighbors
* 

## Other notes
Environement Class
Bitmap Class
- Lollipop SDK
