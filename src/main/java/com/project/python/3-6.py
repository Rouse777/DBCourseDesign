#-*- coding: UTF-8 -*-
import numpy as np
import os
'''
输入：用户的两张表tbMROdata 与 tbC2I
输出：
    主小区ID
    邻小区ID
    主邻小区RSRP差值的均值
    主邻小区RSRP差值的标准差
    主邻小区RSRP差值小于9的概率
    主邻小区RSRP差值绝对值小于6的概率
'''
'''
主邻小区C2I干扰分析
涉及到 tbMROdata:1MB  tbC2I:50MB   (可放入内存计算)
'''


parent = os.path.dirname(os.path.realpath(__file__))
garder = os.path.dirname(parent)
temp1path=os.path.dirname(garder)
temp2path=os.path.dirname(temp1path)
anspath=os.path.dirname(temp2path)


# 文件路径
PATH_MRO = "/root/server/data/tbMROData.csv"

PATH_C2I ="/root/server/data/tbC2I.csv"

PATH_OUT = "/root/server/data/tbC2Inew.csv"



# 读文件并存起来
'''
TimeStamp	ServingSector	InterferingSector	LteScRSRP	LteNcRSRP	LteNcEarfcn	LteNcPci
                                                主小区功率   邻小区功率
C2I = 主小区功率 - 邻小区功率
C2I值越小，说明邻小区对主小区的干扰越大。

1. 一对主邻小区会有多个测量值
2. C2I可以看成符合正态分布的随机变量，因此可以计算     C2I_mean 和 标准差std


根据上述信息，计算：
1.主邻小区RSRP差值小于9的概率  PrbC2I9
2.主邻小区RSRP差值小于6的概率  PrbABS6

'''

'''
设计数据结构：
dict[(S,N)] = set()  set()为差值的集合

用这个字典即可算出题目要求的全部内容
'''
s_n = {}
with open(PATH_MRO,'r') as myFile:
    myFile.readline()
    while True:
        line = myFile.readline()
        if not line:
            break
        s = line.split(',')[1]
        n = line.split(',')[2]
        c2i = float(line.split(',')[3])- float(line.split(',')[4])
        if(s,n) in s_n:
            s_n[(s,n)].append(c2i)
        else:
            s_n[(s,n)] = []
            s_n[(s,n)].append(c2i)


output = open(PATH_OUT,'a')
output.write('scell,ncell,C2I_mean,C2I_std,Prb9,Prbabs6\n')

counttt = 0
for sn in s_n:
    # 求均值
    # print(s_n[sn])
    if len(s_n[sn]) > 15:
        mean = np.mean(s_n[sn])
        # 求标准差
        std = np.std(s_n[sn], ddof=1)
        # 差值小于9的概率
        count_9 = 0
        count_6 = 0
        for num in s_n[sn]:
            if num < 9:
                count_9 += 1
        # 差值绝对值小于6的概率
        for num in s_n[sn]:
            if -6 <num<6:
                count_6 += 1
        pro_9 = count_9 / len(s_n[sn])
        pro_6 = count_6 / len(s_n[sn])
        output_str = sn[0] + ',' + sn[1] + ',' + str(mean) + ',' + str(std) + ',' + str(pro_9) + ',' + str(pro_6) + '\n'
        output.write(output_str)


'''
CITY	SCELL	NCELL	PrC2I9	C2I_Mean	std	SampleCount	WeightedC2I

with open(PATH_C2I,'r') as myFile:
    myFile.readline()
    while True:
        line = myFile.readline()
        if not line:
            break

'''




