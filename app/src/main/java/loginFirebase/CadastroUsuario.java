package loginFirebase;


import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.br.gestao_de_ensino.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.br.gestao_de_ensino.Activity.MainActivity;

public class CadastroUsuario extends AppCompatActivity {

    private EditText edt_email_cadastro;
    private EditText edt_password_cadastro;
    private EditText edt_confirmar_senha_cadastro;
    private CheckBox ckb_mostrar_senha_cadastro;
    private Button btn_cadastrar;
    private Button btn_login_cadastro;
    private ProgressBar loginProgressBar_cadastro;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        mAuth = FirebaseAuth.getInstance();

        edt_email_cadastro = findViewById(R.id.edt_email_cadastro);
        edt_password_cadastro = findViewById(R.id.edt_password_cadastro);
        edt_confirmar_senha_cadastro = findViewById(R.id.edt_confirmar_senha_cadastro);
        ckb_mostrar_senha_cadastro = findViewById(R.id.ckb_mostrar_senha_cadastro);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        btn_login_cadastro = findViewById(R.id.btn_login_cadastro);
        loginProgressBar_cadastro = findViewById(R.id.loginProgressBar_cadastro);
        ckb_mostrar_senha_cadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_password_cadastro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_confirmar_senha_cadastro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edt_password_cadastro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirmar_senha_cadastro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String cadastroEmail = edt_email_cadastro.getText().toString();
                String senha = edt_password_cadastro.getText().toString();
                String confirmarSenha = edt_confirmar_senha_cadastro.getText().toString();

                if (!cadastroEmail.isEmpty() && !senha.isEmpty() && !confirmarSenha.isEmpty()) {
                    if (senha.equals(confirmarSenha)) {
                        loginProgressBar_cadastro.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(cadastroEmail, senha)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    abrirTelaPrincipal();
                                } else {
                                    String error = task.getException().getMessage();
                                    System.out.println(error);
                                    String forma_inv = String.format(getString(R.string.formato_invalido_v1));
                                    Snackbar.make(view,forma_inv,
                                            Snackbar.LENGTH_SHORT).show();
                                    loginProgressBar_cadastro.setVisibility(View.INVISIBLE);
                                }
                                loginProgressBar_cadastro.setVisibility(View.INVISIBLE);
                            }
                        });

                    } else {

                        String senha_mesam_campo = String.format(getString(R.string.senha_mesma_ambos_campos_v1));
                        Snackbar.make(view,
                                senha_mesam_campo,
                                Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    String preencha_campos = String.format(getString(R.string.preencha_campos_v1));
                    Snackbar.make(view,
                            preencha_campos,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btn_login_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastroUsuario.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(CadastroUsuario.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
