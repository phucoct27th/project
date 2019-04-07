package com.phuc.mediaapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, tvTime, tvTimeTotal;
    SeekBar seekBar;
    ImageButton  imBtPre, imBtStop,imBtPlay,imBtNext;
    ImageView imageView;
    ArrayList<Song> arraySong;
    int postion = 0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.dics_rotate);

        InitMediaPlayer();
        imBtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postion++;
                if (postion> arraySong.size()-1){
                    postion =0;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                imBtPlay.setImageResource(R.drawable.ic_pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        imBtPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postion--;
                if (postion <0){
                    postion =arraySong.size()-1;
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                imBtPlay.setImageResource(R.drawable.ic_pause);
                SetTimeTotal();
                UpdateTimeSong();
            }
        });
        imBtStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                imBtPlay.setImageResource(R.drawable.ic_play);
                InitMediaPlayer();
            }
        });
        imBtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imBtPlay.setImageResource(R.drawable.ic_play);
                }
                else {
                    mediaPlayer.start();
                    imBtPlay.setImageResource(R.drawable.ic_pause);
                }
                SetTimeTotal();
                UpdateTimeSong();
                imageView.startAnimation(animation);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }
    private void UpdateTimeSong(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat DinhDangGio = new SimpleDateFormat("mm:ss");
                tvTime.setText(DinhDangGio.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());//update progreess cua seekbar
                //kiem tra thoi gian bai hat, neu ket thuc-> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        postion++;
                        if (postion> arraySong.size()-1){
                            postion =0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        InitMediaPlayer();
                        mediaPlayer.start();
                        imBtPlay.setImageResource(R.drawable.ic_pause);
                        SetTimeTotal();
                        UpdateTimeSong();
                    }
                });
                handler.postDelayed(this, 500);

            }
        }, 100);
    }
    private void SetTimeTotal(){
        SimpleDateFormat DinhdangGio = new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(DinhdangGio.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void InitMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(postion).getFile());
        tvTitle.setText(arraySong.get(postion).getTitle());
    }
    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Não cá vàng", R.raw.naocavang));
        arraySong.add(new Song("Dấu hỏi lớn", R.raw.dauhoilon));
        arraySong.add(new Song("i need you", R.raw.ineedyou));
    }
    private void AnhXa(){
        tvTime      = (TextView) findViewById(R.id.tvTime);
        tvTimeTotal = (TextView) findViewById(R.id.tvTimeTotal);
        tvTitle     = (TextView) findViewById(R.id.tvTitle);
        seekBar     = (SeekBar) findViewById(R.id.seekBar);
        imBtNext    = (ImageButton) findViewById(R.id.imBtNext);
        imBtPlay    = (ImageButton) findViewById(R.id.imBtPlay);
        imBtPre     = (ImageButton) findViewById(R.id.imBtPre);
        imBtStop    = (ImageButton) findViewById(R.id.imBtStop);
        imageView = (ImageView) findViewById(R.id.imageView2);    }
}
