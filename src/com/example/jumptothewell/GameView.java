package com.example.jumptothewell;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
	// 屏幕的宽和高
	private int screenWidth = 0;
	private int screenHeight = 0;
	// 画棋盘的起始位置
	private int startX = 20;
	private int startY = 100;
	// 棋盘宽度
	private int gridWidth = 0;
	private int gridHeight = 0;
	// 画笔
	private Paint paint = null;

	// 表示棋子的数组，其中数组中的每一个元素代表棋盘上的一点
	private int[] chess = new int[5];
	// 表示棋子的颜色，1代表蓝色，2代表红色，0代表没有棋子
	private int CHESS_BLUE = 0xff0000ff;
	private int CHESS_RED = 0xffff0000;

	// 用于记录上一次下的棋子的颜色，1为蓝色，2为红色，-1为刚开始下棋
	private int chess_flag = -1;
	// 判断输赢的标志
	private boolean winFlag = false;
	
	
	public GameView(Context context) {
		super(context);

		// 获取焦点，以感知触屏事件
		this.setFocusable(true);
		// 实例化一个画笔
		paint = new Paint();
		// 设置画笔去锯齿，使图边缘圆滑
		paint.setAntiAlias(true);
	}

	// 重写 View 中的 onDraw 方法
	// 该方法主要承担绘图工作，每刷新一次，就调用一次该方法
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		screenWidth = this.getWidth();
		screenHeight = this.getHeight();
		gridWidth = (int) (screenWidth * 0.8);
		gridHeight = (int) (gridWidth * 0.618);
		startX = (screenWidth - gridWidth) / 2;
		startY = (screenHeight - gridHeight) / 2;
		
		// 把屏幕的底色绘成白色
		// 此处不仅仅是绘色作用，还有刷屏作用，对之前的绘制进行清除
		canvas.drawColor(0xffffffff);
		// TODO 将以上语句注释掉试试看效果
		// 画笔变成黑色
		paint.setColor(0xff000000);
		// 绘制棋盘
		for (int i = 0; i < 2; i++) {
			// 画纵线
			canvas.drawLine(startX + i * gridWidth, startY, 
					startX + i * gridWidth, startY + gridHeight, 
					paint);
			// 画斜线
			canvas.drawLine(startX + i * gridWidth, startY, 
					startX + (1 - i) * gridWidth, startY + gridHeight, 
					paint);
		}
		// 画横线
		canvas.drawLine(startX, startY + gridHeight, 
				startX + gridWidth, startY + gridHeight, paint);
		// 画井
		paint.setColor(0xff19a7f1);
		canvas.drawCircle(screenWidth / 2, screenHeight / 2 - gridHeight / 2,
				gridWidth * 0.1f, paint);

		//绘制棋子
		if(chess_flag == -1) {
			chess[0] = CHESS_BLUE;
			chess[1] = CHESS_BLUE;
			chess[2] = CHESS_RED;
			chess[3] = CHESS_RED;
			chess[4] = 0;
		}
		
		paint.setColor(chess[0]);
		canvas.drawCircle(startX, startY, gridWidth * 0.08f, paint);
		paint.setColor(chess[1]);
		canvas.drawCircle(startX + gridWidth, startY, gridWidth * 0.08f, paint);
		paint.setColor(chess[2]);
		canvas.drawCircle(startX, startY + gridHeight, gridWidth * 0.08f, paint);
		paint.setColor(chess[3]);
		canvas.drawCircle(startX + gridWidth, startY + gridHeight, gridWidth * 0.08f, paint);
		paint.setColor(chess[4]);
		canvas.drawCircle(startX + gridWidth/2, startY + gridHeight/2, gridWidth * 0.08f, paint);
	}
	
	// 重写 View 监听触摸事件方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float touchX = event.getX();
		float touchY = event.getY();
		int index = -1;
		
		if(touchX > startX - 0.08f * gridWidth
				&& touchX < startX + 0.08f * gridWidth) {
			if(touchY > startY - 0.08f * gridWidth
					&& touchY < startY + 0.08f * gridWidth) {
				index = 0;
			} else	if(touchY > startY + gridHeight -0.08f * gridWidth
					&& touchY < startY + gridHeight + 0.08f * gridWidth) {
				index = 2;
			}
		} else	if(touchX > startX + gridWidth - 0.08f * gridWidth
				&& touchX < startX + gridWidth + 0.08f * gridWidth) {
			if(touchY > startY - 0.08f * gridWidth
					&& touchY < startY + 0.08f * gridWidth) {
				index = 1;
			} else	if(touchY > startY + gridHeight -0.08f * gridWidth
					&& touchY < startY + gridHeight + 0.08f * gridWidth) {
				index = 3;
			}
		} else	if(touchX > startX + gridWidth/2 - 0.08f * gridWidth
				&& touchX < startX + gridWidth/2 + 0.08f * gridWidth
				&& touchY > startY + gridHeight/2 - 0.08f * gridWidth
				&& touchY < startY + gridHeight/2 + 0.08f * gridWidth){
			index = 4;
		}
		
		System.out.println("index: "+index);
		
		for(int i=0; i<5; i++){
			if(chess[i] != 0){
				chess[i] = chess[i] | 0xff000000;
			}
		}
		if (index != -1) {
			chess_flag = chess[index];	
			chess[index] = chess[index] & 0xa0ffffff;
		}
		invalidate();
		return super.onTouchEvent(event);
	}
}
