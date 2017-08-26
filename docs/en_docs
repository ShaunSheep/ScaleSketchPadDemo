
# SketchPad Principle：

Notice : Pathview not have draw function ，PointPath have

pathview doc，讲解pathview的事件处理



# Basis ：

View具有onTouchEvent(Motionevent event)函数，可以接收触摸事件

图，描述用户一次触摸滑动的过程，系统产生event的情况；打印log，画图无穷小的点


action test: finger down
action test: finger move
action test: finger move
action test: finger move
action test: finger move
action test: finger move
action test: finger move
action test: finger move
action test: finger up


# Design

Uml图




PointPath have drawn function：
1. save point state
2. receive  Canvas to draw

PathView controller the PointPath
1. receive Touch evnent and respond to do sth
2. save Path which can undo or invalidte
3. onDraw()  

