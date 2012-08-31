# Start the RServer and then interpret the source!
# Open the Expression dialog and then enter
# n1 to see the results in the console !

L<-matrix(0,nrow=4,ncol=4)
#Birth-rates
L[1,]<-c(0,4,3,1)
#Probability of survival
L[2,1]<-0.7
L[3,2]<-0.9
L[4,3]<-0.5

#Abundance
n0<-c(9,5,4,2)

#Abundance after one timestep t+1
n1<-L%*%n0

print("The result is:")
n1
