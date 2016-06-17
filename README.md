Build with make.
Run with ./run-ea.sh $population $points $generations

$ time ./run-ea.sh 5 5000 200 > /tmp/res; sed '/^$/q' /tmp/res > /tmp/res1; sed '1,/^$/d' /tmp/res > /tmp/res2

$ python vis/plot.py /tmp/res1& python vis/plot.py /tmp/res2

