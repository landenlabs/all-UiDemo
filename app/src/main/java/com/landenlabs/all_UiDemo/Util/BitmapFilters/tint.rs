#pragma version(1)
#pragma rs_fp_relaxed
#pragma rs java_package_name(com.landenlabs.all_UiDemo.Util)

/**
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author Dennis Lang  (3/21/2015)
 * @see https://landenlabs.com
 *
 */

#include "rs_graphics.rsh"

// Tint image

// Following gXXXX are set by calling java code.

rs_allocation gIn;
rs_allocation gOut;
rs_script gScript;

// Tint color channels.
float gRed;
float gGreen;
float gBlue;
float gAlpha;


void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
    float4 in4 = rsUnpackColor8888(*v_in);

    float3  tint3 =  {gRed, gGreen, gBlue};
    float inAlpha = 1 - gAlpha;
    float3 out3 = in4.rgb * inAlpha + tint3.rgb * gAlpha;
 
    *v_out = rsPackColorTo8888(out3);
}

void filter() {
    rsForEach(gScript, gIn, gOut);
}