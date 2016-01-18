package com.star.lockpattern.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.star.lockpattern.R;
import com.star.lockpattern.util.LockPatternUtil;

import java.util.List;

/**
 * indicator(three rows and three columns)
 * @author Sym
 */
public class LockPatternIndicator extends View {
	
	private int width, height;
	private int cellBoxWidth, cellBoxHeight;
	private int radius;
	private int offset = 2;
	private Paint defaultPaint, selectPaint;
	private IndicatorCell[][] mIndicatorCells = new IndicatorCell[3][3];

	private static final String TAG = "LockPatternIndicator";

	public LockPatternIndicator(Context context) {
		this(context, null);
	}

	public LockPatternIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public LockPatternIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.init();
	}
	
	private void init(){
		//initViewSize(context, attrs);
		initRadius();
		initPaint();
		init9IndicatorCells();
	}

	/**
	 * init view size
	 * @param context
	 * @param attrs
     */
	@Deprecated
	private void initViewSize(Context context, AttributeSet attrs){
		for(int i = 0; i < attrs.getAttributeCount(); i ++){
			String name = attrs.getAttributeName(i);
			if("layout_width".equals(name)){
				String value = attrs.getAttributeValue(i);
				this.width = LockPatternUtil.changeSize(context, value);
				//Log.e(TAG, "layout_width:" + value);
			}
			if("layout_height".equals(attrs.getAttributeName(i))){
				String value = attrs.getAttributeValue(i);
				this.height = LockPatternUtil.changeSize(context, value);
				//Log.e(TAG, "layout_height:" + value);
			}
		}
		//check the width is or not equals height
		//if not throw exception
		if (this.width != this.height) {
			throw new IllegalArgumentException("the width must be equals height");
		}
	}
	
	private void initRadius() {
		this.radius = (this.width - offset*2)/4/2;
		this.cellBoxHeight = (this.height - offset*2)/3;
		this.cellBoxWidth = (this.width - offset*2)/3;
	}
	
	private void initPaint() {
		defaultPaint = new Paint();
		defaultPaint.setColor(getResources().getColor(R.color.grey_b2b2b2));
		defaultPaint.setStrokeWidth(3.0f);
		defaultPaint.setStyle(Style.STROKE);
		defaultPaint.setAntiAlias(true);
		
		selectPaint = new Paint();
		selectPaint.setColor(getResources().getColor(R.color.blue_01aaee));
		selectPaint.setStrokeWidth(3.0f);
		selectPaint.setStyle(Style.FILL);
		selectPaint.setAntiAlias(true);
	}

	/**
	 * initialize nine cells
	 */
	private void init9IndicatorCells(){
		int distance = this.cellBoxWidth + this.cellBoxWidth/2 - this.radius;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mIndicatorCells[i][j] = new IndicatorCell(distance*j + radius + offset, distance*i + radius + offset, 3*i + j + 1);
			}
		}
	}

	/**
	 * set nine indicator cells size
	 */
	private void set9IndicatorCellsSize() {
		int distance = this.cellBoxWidth + this.cellBoxWidth/2 - this.radius;
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				mIndicatorCells[i][j].setX(distance*j + radius + offset);
				mIndicatorCells[i][j].setY(distance*i + radius + offset);
			}
		}
	}

	/**
	 * set indicator
	 * @param cells
     */
	public void setIndicator(List<LockPatternView.Cell> cells) {
		for(LockPatternView.Cell cell : cells) {
			for(int i = 0; i < mIndicatorCells.length; i++) {
				for(int j = 0; j < mIndicatorCells[i].length; j++) {
					if (cell.getIndex() == mIndicatorCells[i][j].getIndex()) {
						//Log.e(TAG, String.valueOf(cell.getRow() * 3 + cell.getColumn() + 1));
						mIndicatorCells[i][j].setStatus(IndicatorCell.STATE_CHECK);
					}
				}
			}
		}
		this.postInvalidate();
	}

	/**
	 * set default indicator
	 */
	public void setDefaultIndicator() {
		for(int i = 0; i < mIndicatorCells.length; i++) {
			for(int j = 0; j < mIndicatorCells[i].length; j++) {
				mIndicatorCells[i][j].setStatus(IndicatorCell.STATE_NORMAL);
			}
		}
		this.postInvalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawToCanvas(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.width = getMeasuredWidth();
		this.height = getMeasuredHeight();
		if (this.width != this.height) {
			throw new IllegalArgumentException("the width must be equals height");
		}
		this.initRadius();
		this.set9IndicatorCellsSize();
		this.invalidate();
	}

	/**
	 * draw the view to canvas
	 * @param canvas
     */
	private void drawToCanvas(Canvas canvas) {
		for(int i = 0; i < mIndicatorCells.length; i++) {
			for(int j = 0; j < mIndicatorCells[i].length; j++) {
				if(mIndicatorCells[i][j].getStatus() == IndicatorCell.STATE_NORMAL) {
					canvas.drawCircle(mIndicatorCells[i][j].getX(), mIndicatorCells[i][j].getY(), radius, defaultPaint);
				} else if(mIndicatorCells[i][j].getStatus() == IndicatorCell.STATE_CHECK) {
					canvas.drawCircle(mIndicatorCells[i][j].getX(), mIndicatorCells[i][j].getY(), radius, selectPaint);
				}
			}
		}
	}
	
	public class IndicatorCell {
		private int x;// the center x of circle
		private int y;// the center y of circle
		private int status = 0;//default
		private int index;// the cell value
		
		public static final int STATE_NORMAL = 0;
		public static final int STATE_CHECK = 1;
		
		public IndicatorCell(){}
		
		public IndicatorCell(int x, int y, int index){
			this.x = x;
			this.y = y;
			this.index = index;
		}
		
		public int getX(){
			return this.x;
		}

		public void setX(int x) {
			this.x = x;
		}
		
		public int getY(){
			return this.y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
		public int getStatus(){
			return this.status;
		}
		
		public void setStatus(int status){
			this.status = status;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public void setIndex(int index) {
			this.index = index;
		}
	}
	
}
