# 缩放、平移原理

# 缩放功能设计


   双手触摸屏幕缩放、平移操作，打印出的log：
   
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
   
## 基本知识

1. view 的缩放属性，平移属性 
2. UMl图，viewgroup和子view的事件传递
    
![]( https://github.com/ShaunSheep/ScaleSketchPadDemo/blob/master/docs/sacleuml.png)
    
## 难点

1.  计算缩放比例、控制缩放的大小，保证图片不越界
2.  画板缩放之后，手势画出的线条发生偏移
3.  子view和viewgroup的事件拦截
