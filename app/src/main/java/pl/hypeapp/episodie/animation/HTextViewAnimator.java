package pl.hypeapp.episodie.animation;

import android.os.Handler;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class HTextViewAnimator {
    private HTextView hTextView;
    private String[] textsToAnimate;
    private int textToAnimateLength;
    private Handler animationSequenceHandler;
    private int delayBetweenSequences;
    private Listener listener;

    public HTextViewAnimator(HTextView hTextView, String[] textsToAnimate, HTextViewType hTextViewType) {
        this.hTextView = hTextView;
        this.textsToAnimate = textsToAnimate;
        textToAnimateLength = textsToAnimate.length;
        hTextView.setAnimateType(hTextViewType);
        hTextView.setText(textsToAnimate[0]);
        animationSequenceHandler = new Handler();
        delayBetweenSequences = 1500;
        listener = null;
    }

    public void setDelayBetweenSequences(int delayInMilis) {
        this.delayBetweenSequences = delayInMilis;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void playSequenceAnimation() {
        animateHTextViewSequence(0);
    }

    private void animateHTextViewSequence(final int startTextIndex) {
        animationSequenceHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hTextView.animateText(textsToAnimate[startTextIndex]);
                if (isTextToAnimate()) {
                    animateHTextViewSequence(startTextIndex + 1);
                } else {
                    if(listener!= null){
                        listener.onAnimationEnd();
                    }
                }
            }

            private boolean isTextToAnimate() {
                return startTextIndex < textToAnimateLength - 1;
            }
        }, delayBetweenSequences);
    }

    public interface Listener{
            void onAnimationEnd();
        }
}
