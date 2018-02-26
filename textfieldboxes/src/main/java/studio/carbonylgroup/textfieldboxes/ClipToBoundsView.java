package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/08/25
 */
class ClipToBoundsView extends RelativeLayout {

    Context context;
    Float cornerRadius;
    Rect rect = new Rect();
    RectF rectF = new RectF();
    Path clipPath = new Path();

    public ClipToBoundsView(Context context) {

        super(context);
        this.context = context;
        init();
    }

    public ClipToBoundsView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        init();
    }

    public ClipToBoundsView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    protected void init() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        cornerRadius = context.getResources().getDimension(R.dimen.corner_radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.getClipBounds(rect);
        rectF.set(rect);
        clipPath.reset();
        clipPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
