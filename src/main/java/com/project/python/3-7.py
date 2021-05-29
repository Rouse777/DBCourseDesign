#-*- coding: UTF-8 -*-
# x = int(input())  # 系统中由用户来定义x

'''
输入：tbC2Inew
输出：tbC2I3
'''
import os
parent = os.path.dirname(os.path.realpath(__file__))
garder = os.path.dirname(parent)
temp1=os.path.dirname(garder)
temp2=os.path.dirname(temp1)
ans=os.path.dirname(temp2)


# 第一步：找出全部的三元组
import sys
# 读入文件
x=int(sys.argv[1])

PATH_IN = '/root/server/data/tbC2Inew.csv'
PATH_OUT ='/root/server/data/tbC2I3.csv'
output = open(PATH_OUT,'a')
def proc(x):
    nodes = set()
    edge = set()
    edge_weight = {}
    with open(PATH_IN,'r') as myFile:
        myFile.readline()
        while True:
            line = myFile.readline()
            if not line:
                break
            s = line.split(',')[0]
            n = line.split(',')[1]
            weight = float(line.split(',')[5])
            nodes.add(s)
            nodes.add(n)
            edge.add((s,n))
            edge.add((n,s))
            edge_weight[(s,n)] = weight
            edge_weight[(n,s)] = weight




    triple = set()
    for node1 in nodes:
        for node2 in nodes:
            for node3 in nodes:
                if(node1,node2) in edge and (node1,node3) in edge and (node2,node3) in edge \
                    and (node1,node2,node3) not in triple and (node1,node3,node2) not in triple\
                    and (node2,node1,node3) not in triple and (node2,node3,node1) not in triple\
                    and (node3,node1,node2) not in triple and (node3,node2,node1) not in triple :
                    new_set = set()
                    new_set.add(node1)
                    new_set.add(node2)
                    new_set.add(node3)
                    triple.add((node1,node2,node3))



    base_line = float(float(x)/100)
    for item in triple:
        a = item[0]
        b = item[1]
        c = item[2]
        ab = edge_weight[(a,b)]
        ac = edge_weight[(a,c)]
        ba = edge_weight[(b,a)]
        bc = edge_weight[(b,c)]
        ca = edge_weight[(c,a)]
        cb = edge_weight[(c,b)]
        if  (ab >= base_line or ba >=base_line) and \
                (bc >= base_line or cb>= base_line) and \
                (ac >= base_line or ca >= base_line):
            output.write(a + ',' + b + ',' + c + '\n')
proc(x)



'''
算法：
第一步：
    深度为1的dfs，找到这个点的全部相邻节点

第二步：
    这些相邻节点之间有没有相邻的
'''








