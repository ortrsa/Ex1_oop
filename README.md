# Ex1 oop

# Overveiw 

# Dijkstra Algorithm

![alt text](https://i.ibb.co/G25wb87/Dijkstra-Ex1.png)

# Uses in reality
Here some sample for using this program in real life:
for example, we need to fined the best way from Tel Aviv to Ashdod.
at the firest example all road heve the same weight no traffic of accidents.
at the second example thar is heavy traffic jam at the road between Holon and Ashdod and between Rishon Lezion and Rehovot.

### Regular map
[![image](https://www.linkpicture.com/q/צילום-מסך-2020-11-19-ב-12.19.02_1.png)](https://www.linkpicture.com/view.php?img=LPic5fb65455dfb331797244551)
#### Input:
```

WGraph_DS IsraelMap = new WGraph_DS();
        IsraelMap.addNode(1);
        IsraelMap.getNode(1).setInfo("Tel Aviv");
        IsraelMap.addNode(2);
        IsraelMap.getNode(2).setInfo("Holon");
        IsraelMap.addNode(3);
        IsraelMap.getNode(3).setInfo("Rishon Lezion");
        IsraelMap.addNode(4);
        IsraelMap.getNode(4).setInfo("Rehovot");
        IsraelMap.addNode(5);
        IsraelMap.getNode(5).setInfo("Yavne");
        IsraelMap.addNode(6);
        IsraelMap.getNode(6).setInfo("Ashdod");
        IsraelMap.connect(1,2,1);
        IsraelMap.connect(3,2,1);
        IsraelMap.connect(6,2,1);
        IsraelMap.connect(3,4,1);
        IsraelMap.connect(3,5,1);
        IsraelMap.connect(5,4,1);
        IsraelMap.connect(5,6,1);
        WGraph_Algo IsraelMapAlgo = new WGraph_Algo();
        IsraelMapAlgo.init(IsraelMap);
        IsraelMapAlgo.shortestPath(1,6);
        System.out.println(IsraelMapAlgo.PathtoString());
```





### Trafficed map 
[![image](https://www.linkpicture.com/q/צילום-מסך-2020-11-19-ב-12.15.51.png)](https://www.linkpicture.com/view.php?img=LPic5fb646102beca472179639)

