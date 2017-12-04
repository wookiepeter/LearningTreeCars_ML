import subprocess
import os

def train_net():
    args = ("./mlpt", "-c2", "-e1000", "domains.cars", "data/car.data", "curnet.mlp")
    #Or just:
    #args = "./mlpt -c2 -e1000 domains.cars data/car.data curnet.mlp".split()
    popen = subprocess.Popen(args, stdout=subprocess.PIPE)
    popen.wait()
    output = popen.stdout.read()
    print(output)

def execute_net():
    args = "./mlpx curnet.mlp data/car.data result.out &> tmp"
    #args = ("./mlpx",  "curnet.mlp", "data/car.data", "result.out")# "&>", "tmp")
    #Or just:
    #args = "./mlpt -c2 -e1000 domains.cars data/car.data curnet.mlp".split()
    #popen = subprocess.Popen(args, stdout=subprocess.PIPE)
    #popen = subprocess.Popen(args,shell=True,stderr=subprocess.PIPE)
    
    #popen.communicate()

    #for line in iter(popen.stdout.readline, ''):
    #    print(line)
    #popen.stdout.close()
    #if(popen.wait() != 0):
    #    raise RuntimeError("%r failed, exit statuts: %d" % (args, popen.returncode))
    #output = popen.stdout.read()

    #popen.wait()
    #output = popen.communicate()

    #popen = subprocess.check_call(args, shell=True)
    #popen.communicate()

    #subprocess.check_call(args)
    print(os.getcwd())
    os.system('./mlpx curnet.mlp data/car.data result.out &> tmp')

    #list = []
    #f = open("tmp", "r")
    #for line in f:
    #    list.append(line)
    #lastLine = list[len(list) - 1]
    #print (len(li))
    #    print("printing: " + str(output) + " ... done!")


train_net()
print(" ---------------------------------------------- ")
execute_net()