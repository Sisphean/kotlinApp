package com.sisyphean.kotlinapp.ui.activity

import android.app.ProgressDialog
import android.view.View
import androidx.lifecycle.Observer
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.base.BaseVMActivity
import com.sisyphean.kotlinapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseVMActivity<LoginViewModel>(), View.OnClickListener {

    override fun setLayoutId(): Int = R.layout.activity_login
    //LoginViewModel::class取得是KCLass,调用java获取java的Class
    override fun getVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {
        btn_login.setOnClickListener(this)
    }

    override fun subscribe() {
        /*mViewModel.loginData.observe(this, Observer {

            it?.let {
                if (it == 1) Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
            }
        })*/
        mViewModel.apply {
            loginStateLiveData.observe(this@LoginActivity, Observer {
                if (it.isLoading) showProgressDialog()
            })

            loginLiveData.observe(this@LoginActivity, Observer {

                dismissProgressDialog()
                toast(it.user.toString())
            })
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> mViewModel.login(
                    tv_username.text.toString().trim(),
                    tv_password.text.toString().trim())
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }


}
