package pl.hypeapp.episodie.util.animation;

import android.os.Handler;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

public class HtextViewAnimator {
    private HTextView hTextView;
    private String[] textsToAnimate;
    private int textToAnimateLength;
    private Handler animationSequenceHandler;
    private int delayBetweenSequences;
    private Listener listener;

    public HtextViewAnimator(HTextView hTextView, String[] textsToAnimate, HTextViewType hTextViewType) {
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
        if(animationSequenceHandler != null) {
            animationSequenceHandler.postDelayed(sequenceRun(startTextIndex), delayBetweenSequences);
        }
    }

    private Runnable sequenceRun(final int startTextIndex){
        return new Runnable() {
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
        };
}

    public void stopSequenceAnimation(){
        if(animationSequenceHandler != null) {
            animationSequenceHandler.removeCallbacks(sequenceRun(0));
            animationSequenceHandler = null;
            hTextView.reset(hTextView.getText());
        }
    }

    public interface Listener{
            void onAnimationEnd();
        }
}
