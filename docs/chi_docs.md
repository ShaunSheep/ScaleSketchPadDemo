
# 绘画板原理：

注意: athview 没有绘制的能力，具备绘制能力的是PointPath

这张图说明了：事件传递的过程、处理事件的方式

![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/patchviewdoc_chi.png)

# 基本知识：

View具有onTouchEvent(Motionevent event)函数，可以接收触摸事件

图，描述用户一次触摸滑动的过程，系统产生event的情况；打印log，画图无穷小的点


#rotate {
    width: 300.11px;
    height: 200px;
    transform: rotate(-90deg) translate(-50px,-50px) ;
}
 <img src="https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/drawpath.jpeg" width = "360" height = "640" alt="draw path" align=center id="rotate "/>

 action test: finger down
 action test: finger move
 action test: finger move
 action test: finger move
 action test: finger move
 action test: finger move
 action test: finger move
 action test: finger move
 action test: finger up


# 实现思路

![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/uml.png)

PointPath 是实际具有绘画能力的类：
1. 存储点的状态（橡皮、普通点、粗细、颜色）
2. 接收Canvas进行绘制

PathView  是PointPath的控制类,继承自View：
1. 接收Touch事件，并处理响应的event
2. 存储每一条Path
3. onDraw()中调用PointPath类进行绘制


# 难点

1. View 的 onTouchEvent()
    1. 每接收到一个Event，都需要break结束判断
    2. 存储Point的时机——>Action_Move——>记得break结束判断流程
    3. 存储Path的时机——>Action_Up——>记得break结束判断流程
    4. move和up结束前，都要invalidte
2. 橡皮擦功能
    1. Paint.setXformode()属性，本质就是同一个位置，两个点进行集合运算
    2. onDraw()中需要新建临时canvas，临时Bitmap
