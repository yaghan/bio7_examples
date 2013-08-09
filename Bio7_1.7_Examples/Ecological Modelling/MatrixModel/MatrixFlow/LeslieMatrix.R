

L<-matrix(0,nrow=4,ncol=4)
#Birth-rates
L[1,]<-c(1,1,1,1)
#Probability of survival
L[2,1]<-1.0
L[3,2]<-1.0
L[4,3]<-1.0

#Abundance
n0<-c(1,1,1,1)



