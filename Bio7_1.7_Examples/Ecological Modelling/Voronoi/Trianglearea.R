#Evaluate x,y-values in the Points panel and transfer them
#to R before you start this script !

library(tripack)
tri.vm <- voronoi.mosaic(x,y,duplicate="remove")
tri.vm.areas <- voronoi.area(tri.vm)

#Mark the following lines with the "Set Plotmarker" action in the context menu !

#In Java the 0,0 coordinate is in the top-left!!!
#The 0,0 coordinate of plots in R is the bottom left by default !!!!
#We plot it for Java! The fieldy variable will be automatically transferred
#when we use the Points action in the "Image Methods" view
plot(tri.vm,ylim=c(255,0))
text(x,y, tri.vm.areas, cex=0.2)
points(x,y,cex=0.5)