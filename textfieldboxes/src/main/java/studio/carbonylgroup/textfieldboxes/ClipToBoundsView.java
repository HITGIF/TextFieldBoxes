package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/08/25
 */
public class ClipToBoundsView extends RelativeLayout {

    Context context;

    public ClipToBoundsView(Context context) {

        super(context);
        this.context = context;
    }

    public ClipToBoundsView(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
    }

    public ClipToBoundsView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Path clipPath = new Path();
        Float cornerRadius = context.getResources().getDimension(R.dimen.text_field_boxes_corner_radius);
        clipPath.addRoundRect(new RectF(canvas.getClipBounds()), cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
