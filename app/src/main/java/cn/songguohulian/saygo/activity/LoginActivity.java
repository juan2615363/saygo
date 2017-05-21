package cn.songguohulian.saygo.activity;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mingle.widget.ShapeLoadingDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.songguohulian.saygo.R;
import cn.songguohulian.saygo.base.BaseActivity;
import cn.songguohulian.saygo.bean.SayUser;
import cn.songguohulian.saygo.utils.EssentialUtils;
import cn.songguohulian.saygo.utils.MyConstant;
import cn.songguohulian.saygo.utils.SpTools;
import cn.songguohulian.saygo.utils.ToastUtil;


/**
 *
 * @author Ziv
 * @data 2017/5/18
 * @time 15:19
 *
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    @Bind(R.id.edt_login_username)
    public EditText mUserName;


    @Bind(R.id.edt_login_password)
    public EditText mPassWord;


    @Bind(R.id.text_register)
    public TextView mRegister;


    @Bind(R.id.text_forget_pwd)
    public TextView mForgetPass;

    @Bind(R.id.btn_login)
    public Button mLoginButton;


    @Bind(R.id.login_cancle)
    public ImageView imageView;

    private String userName;
    private String password;
    private ShapeLoadingDialog shapeLoadingDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        // 初始化点击事件
        mRegister.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    /*
    * 点击事件处理
    * */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_forget_pwd:
                // 跳转到忘记密码界面
                Intent intent_forget = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent_forget);
                break;
            case R.id.text_register:
                // 跳转到登录界面
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_register);
                break;
            case R.id.btn_login:
                if (EssentialUtils.isNetworkAvailable(this) && checkInput()) {
                    shapeLoadingDialog = new ShapeLoadingDialog(this);
                    shapeLoadingDialog.setLoadingText("登录中...");
                    shapeLoadingDialog.show();

                    BmobUser.loginByAccount(this, userName, password, new LogInListener<SayUser>() {
                        @Override
                        public void done(SayUser sayUser, BmobException e) {
                            if (e == null) {
                                shapeLoadingDialog.dismiss();
                                // 保存用户状态 直接进入主界面
                                SpTools.putBoolean(getApplicationContext(), MyConstant.ISLOGIN, true);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                ToastUtil.toastShort(getApplicationContext(), "登录失败!");
                                shapeLoadingDialog.dismiss();
                            }
                        }
                    });
                } else if (!EssentialUtils.isNetworkAvailable(this)) {
                    ToastUtil.toastShort(this, "网络未连接");
                    return;
                }
                break;
            case R.id.login_cancle:
                System.exit(0);
                break;
        }
    }


    /**
     * 检查输入
     *
     * @return
     */
    private boolean checkInput() {
        userName = mUserName.getText().toString();
        password = mPassWord.getText().toString();

        if (userName.length() > 0 && password.length() > 0) {
            return true;
        } else if (userName.length() <= 0) {
            Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();

        } else if (password.length() <= 0) {
            Toast.makeText(mContext, "密码不能为空", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
