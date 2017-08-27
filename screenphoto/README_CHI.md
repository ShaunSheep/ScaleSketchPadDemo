# ScaleSketchPadDemo

此项目包含两个模块
1. app1 为普通绘画板
2. app2 为可所发的绘画板

方便各位Android 开发者理解和使用

用法：

进入项目根目录：https://github.com/ShaunSheep/ScaleSketchPadDemo

clone or download   项目到本地，

打开Android Studio—>file—>new—>import new moudle—>选中本地的app或aap2

# 项目简介

    | 普通绘画板 | 可缩放平移绘画板
----|------|----
效果图 | ![](https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/normalSkecthPadDemo.png)  |![](https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/scale1.png)   
事件图 | ![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/patchviewdoc_chi.png)  |![]( https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/scaledoc.png) 
uml图 | ![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/uml.png)  | ![]( https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/sacleuml.png)


# 普通绘画板

有五大功能：
1. 普通绘画
2. 在图片上绘画
3. 改变画笔颜色
4. 改变画笔粗细
5. 撤销操作
6. 添加图片

### 绘画功能

 <img src="https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/drawpath.png" width = "360" height = "640" alt="draw path" align=center />

### 在图片上绘画
![](https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/normalSkecthPadDemo.png)

### 改变画笔颜色
![](https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/color.png)

### 改变画笔粗细
![](https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/stroke.png)

### 添加图片
 <img src="https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/addphoto.png" width = "360" height = "640" alt="draw path" align=center />


### 绘画板原理：

注意: Pathview 没有绘制的能力，具备绘制能力的是PointPath

这张图说明了：事件传递的过程、处理事件的方式

![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/patchviewdoc_chi.png)

### 基本知识：

View具有onTouchEvent(Motionevent event)函数，可以接收触摸事件

图，描述用户一次触摸滑动的过程，系统产生event的情况；打印log，画图无穷小的点


 <img src="https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/drawpath_meitu_1.jpg" width = "640" height = "340" alt="draw path" align=center id="rotate "/>

    一次 touch event log：
    
     action test: finger down
     action test: finger move
     action test: finger move
     action test: finger move
     action test: finger move
     action test: finger move
     action test: finger move
     action test: finger move
     action test: finger up


### 实现思路

![](https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/uml.png)

PointPath 是实际具有绘画能力的类：
1. 存储点的状态（橡皮、普通点、粗细、颜色）
2. 接收Canvas进行绘制

PathView  是PointPath的控制类,继承自View：
1. 接收Touch事件，并处理响应的event
2. 存储每一条Path
3. onDraw()中调用PointPath类进行绘制


### 难点

1. View 的 onTouchEvent()
    1. 每接收到一个Event，都需要break结束判断
    2. 存储Point的时机——>Action_Move——>记得break结束判断流程
    3. 存储Path的时机——>Action_Up——>记得break结束判断流程
    4. move和up结束前，都要invalidte
2. 橡皮擦功能
    1. Paint.setXformode()属性，本质就是同一个位置，两个点进行集合运算
    2. onDraw()中需要新建临时canvas，临时Bitmap
    
    




# 可缩放的绘画板


### app2 增加了缩放功能

### 双手缩放平移VS普通状态

 <img src="https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/scale1.png" width = "360" height = "640" alt="draw path" align=center />

 <img src="https://raw.githubusercontent.com/ShaunSheep/ScaleSketchPadDemo/master/screenphoto/scale2.png" width = "360" height = "640" alt="draw path" align=center />

### 缩放、平移原理


#### 缩放功能设计


   双手触摸屏幕缩放、平移操作，打印出的log(也就是系统反馈给开发者的触摸事件)：
   
     action test: finger down
     action test: action_pointer_down
     action test: action_move
     action test: action_move
     action test: action_move
     action test: action_move
     action test: action_move
     action test: action_move
     action test: action_up
    
    
插入图片：缩放图原理
   
  
   ![]( https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/scaledoc.png)
   
### 基本知识

1. view 的缩放属性，平移属性 
   view.setscaleX()
   view.setscaleY()
   view.setX()
   view.setY()
2. viewgroup和子view的事件传递

return true 即为消耗event

![]( https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/sacleuml.png)
    
### 难点

1.  计算缩放比例、控制缩放的大小，保证图片不越界
2.  画板缩放之后，手势画出的线条发生偏移，计算平移值，对坐标点进行加减运算即可
3.  子view和viewgroup的事件拦截
