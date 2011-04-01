#Example from R using the standard package nnet.
#More packages are available-> for example:The AMORE package.

library(nnet)
data(iris3)
# use half the iris data
ir <- rbind(iris3[,,1],iris3[,,2],iris3[,,3])
targets <- class.ind( c(rep("s", 50), rep("c", 50), rep("v", 50)) )
samp <- c(sample(1:50,25), sample(51:100,25), sample(101:150,25))

ir1 <- nnet(ir[samp,], targets[samp,], size = 2, rang = 0.1,
            decay = 5e-4, maxit = 200)
test.cl <- function(true, pred) {
    true <- max.col(true)
    cres <- max.col(pred)
    table(true, cres)
}
#Type "data" in the expression dialog to see the results !
data<-test.cl(targets[-samp,], predict(ir1, ir[-samp,]))

#References:
#Ripley, B. D. (1996) Pattern Recognition and Neural Networks. Cambridge.
#Venables, W. N. and Ripley, B. D. (2002) Modern Applied Statistics with S. Fourth edition. Springer. 