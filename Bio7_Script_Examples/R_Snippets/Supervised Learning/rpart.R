#This Snippet demonstrates the use of supervised learning
#for image analysis.
#
#Please open an R,G,B image and split it into its R,G,B components
#(Image perspective->ImageJ-Canvas->Image->Colours->Split Colours).
# 
#For this example use the Pixel action from the Image-Methods view
#(Image perspective->ImageJ-Canvas->Window->Bio7-Toolbar)
#and transfer 3 different selections (e.g with the freehand selection of ImageJ).
#Call them matrix 1,2,3. In addition select the whole image as matrixAll.
#Signatures from all opened images (or layers) will be transferred.
#For example if you split an R,G,B image the selection bounds of one image
#will be automatically the selection bounds of the other images (layer).

library(rpart)# We use the lib package rpart
m<-rbind(matrix1,matrix2,matrix3)#Combine matrices which represent a special signature.
dftrain<-as.data.frame(m)#Create a dataframe dftrain with the matrices 1-3
res<-rpart(signature~layer0+layer1+layer2,data=dftrain)#Train with the selection matrices combined in one matrix
final<-predict(res,as.data.frame(matrixAll))#Predict the whole image
imageMatrix<-matrix(final,507,446)#Create a image matrix
#You can now transfer the image back as a greyscale image (Image perspective->ImageJ-Canvas->Window->Bio7-Toolbar)
#to ImageJ to see the results!

