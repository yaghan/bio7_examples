#Because Bio7 uses RServe to connect itself with R, plotting is available by help of
#plotmarkes which can be set with the context menu. All variables have to be defined
#before plotting!
#Requires the library "spatial" installed in R!

library(spatial)
pines<-ppinit("pines.dat");
par(mfrow=c(1,2),pty="s")

#Mark the following lines with the "Set Plotmarker" action in the context menu!
plot(pines,pch=16)
plot(Kfn(pines,5),type="s",xlab="dne",ylab="L(t)")
boxplot(pines)
