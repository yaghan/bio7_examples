#This example (modified example from the R-sig-Geo list!)
#creates spatial data in R and transform it for WorldWind
#the data then can be transferred to ImageJ and from ImageJ
#to WorldWind. 
#The area can also be transferred easily
#to WorldWind because the necessary variables are defined, too.

library(maptools)
library(rgdal)

data(meuse.grid) # only the non-missing valued cells
coordinates(meuse.grid) = c("x", "y") # to SpatialPointsDataFrame
proj4string(meuse.grid) <- CRS(paste("+init=epsg:28992", "+towgs84=565.237,50.0087,465.658,-0.406857,0.350733,-1.87035,4.0812"))
gridded(meuse.grid) <- TRUE # promote to SpatialPixelsDataFrame
x = as(meuse.grid, "SpatialGridDataFrame") # creates the full grid

#Create a double matrix which can be tranfered to ImageJ!
imageMatrix<-matrix(x$dist,78,104)#for Bio7!

#Transform for Nasa WorldWind!
meuse.grid<-spTransform(meuse.grid, CRS("+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs +towgs84=0,0,0"))

#Get the bounding box and make the variables for WorldWind available!
bbox<-slot(meuse.grid,"bbox")
minLat <- bbox[2]
maxLat <- bbox[4]
minLon <- bbox[1]
maxLon <- bbox[3]



