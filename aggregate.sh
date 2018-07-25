#!/bin/sh
sort -k5 -n output.txt | awk '
  BEGIN {
    c = 0;
    sum = 0;
  }
  $5 ~ /^[0-9]*(\.[0-9]*)?$/ {
    a[c++] = $5;
    sum += $5;
  }
  END {
    ave = sum / c;
    if( (c % 2) == 1 ) {
      median = a[ int(c/2) ];
    } else {
      median = ( a[c/2] + a[c/2-1] ) / 2;
    }
    if((c % 4) == 0){
      q_1 = (a[c/4]+a[c/4-1])/2;
      q_3 = (a[3*c/4]+a[3*c/4-1])/2;
  } else {
      q_1 = a[int(c/4)];
      q_3 = a[int(3*c/4)];
}
    variance = 0;
    for(i=0;i<c;i++)
        variance+=(a[i]-ave)^2;
    OFS="\t";
    print "NUMBER OF ITERATIONS = " c,
	  "\nAVERAGE TIME (s) = " ave,
	  "\nMEDIAN TIME (s) = " median,
	  "\nMINIMUM TIME (s) = " a[0],
	  "\nMAXIMUM TIME (s) = " a[c-1],
	  "\nPOPULATION VARIANCE (s^2) = " variance/c,
	  "\nSTANDARD DEVIATION (s) = " sqrt(variance/c), 
	  "\nFIRST QUARTILE (25th PERCENTILE) = " q_1, 
	  "\nTHIRD QUARTILE (75th PERCENTILE) = " q_3,
	  "\nINTER-QUARTILE RANGE (IQR) = " q_3 - q_1; }
'
