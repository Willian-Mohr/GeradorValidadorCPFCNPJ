package edu.br.unifcv.seminario;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.br.unifcv.seminario.meupacote.GeraCpfCnpj;

public class MainActivity extends AppCompatActivity {

    private Spinner cmb_documento;
    private RadioButton rdb_validar;
    private RadioButton rdb_gerar;
    private TextView txt_documento;
    private Button btn_vg;
    private TextView txt_result;
    private TextView txt_tipoDoc;
    private TextView txt_numDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Trabalhando com comboBox
        cmb_documento = (Spinner) findViewById(R.id.cmb_documento);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.tipoDocumento, android.R.layout.simple_list_item_activated_1);
        cmb_documento.setAdapter(adapter);

        txt_documento = (TextView) findViewById(R.id.txt_documento);
        rdb_validar = (RadioButton) findViewById(R.id.rdb_validar);
        rdb_gerar = (RadioButton) findViewById(R.id.rdb_gerar);
        btn_vg = (Button) findViewById(R.id.btn_vg);
        txt_result = (TextView) findViewById(R.id.txt_result);
        txt_tipoDoc = (TextView) findViewById(R.id.txt_tipoDoc);
        txt_numDoc = (TextView) findViewById(R.id.txt_numDoc);

        // Validar radioButton -- gerar
        rdb_gerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdb_gerar.isChecked() == true) {
                    txt_documento.setVisibility(View.GONE);
                    btn_vg.setText("GERAR");
                    txt_tipoDoc.setText("");
                    txt_numDoc.setText("");
                    txt_result.setText("");

                }
            }
        });

        // Validar radioButton -- validar
        rdb_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdb_validar.isChecked() == true) {
                    txt_documento.setVisibility(View.VISIBLE);
                    btn_vg.setText("VALIDAR");
                    txt_tipoDoc.setText("");
                    txt_numDoc.setText("");
                    txt_result.setText("");
                    txt_documento.setText("");
                }
            }
        });

        txt_numDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null , txt_numDoc.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Documento copiado com sucesso!", Toast.LENGTH_LONG).show();
            }
        });



        // Ação ao clicar no butto1
        btn_vg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cmb_documento.getSelectedItem().equals("CPF")) {
                    if (rdb_validar.isChecked() == true) {
                        if (String.valueOf(txt_documento.getText()).equals("")) {
                            Vibrar();
                            txt_documento.requestFocus();
                            txt_documento.setError("Algo deu errado. Tente informar um valor");
                        } else {
                            if (String.valueOf(txt_documento.getText()).length() < 11 || String.valueOf(txt_documento.getText()).length() > 11) {
                                Toast.makeText(MainActivity.this, "Um documento CPF contém 11 algarismos e foi informado " + String.valueOf(txt_documento.getText()).length() + ". Tente novamente!", Toast.LENGTH_LONG).show();
                            } else {
                                GeraCpfCnpj validaCPF = new GeraCpfCnpj();
                                String CPF = String.valueOf(txt_documento.getText());
                                boolean result = validaCPF.isCPF(CPF);
                                if (result == true) {
                                    //Toast.makeText(MainActivity.this, "O CPF " + validaCPF.imprimeCPF(CPF) + " é válido!", Toast.LENGTH_LONG).show();
                                    txt_tipoDoc.setTextColor(Color.rgb(0,128,0));
                                    txt_numDoc.setTextColor(Color.rgb(50,205,50));
                                    txt_result.setTextColor(Color.rgb(0,255,0));

                                    txt_tipoDoc.setText("CPF");
                                    txt_numDoc.setText(validaCPF.imprimeCPF(CPF));
                                    txt_result.setText("VÁLIDO");


                                } else {
                                    //Toast.makeText(MainActivity.this, "O CPF " + validaCPF.imprimeCPF(CPF) + " é inválido!", Toast.LENGTH_LONG).show();
                                    txt_tipoDoc.setTextColor(Color.rgb(139,0,0));
                                    txt_numDoc.setTextColor(Color.rgb(165,42,42));
                                    txt_result.setTextColor(Color.rgb(255,0,0));

                                    txt_tipoDoc.setText("CPF");
                                    txt_numDoc.setText(validaCPF.imprimeCPF(CPF));
                                    txt_result.setText("INVÁLIDO");
                                }
                            }
                        }
                    }
                    else if (rdb_gerar.isChecked() == true) {
                        GeraCpfCnpj geraCPF = new GeraCpfCnpj();
                        String CPF = geraCPF.cpf(true);
                        txt_tipoDoc.setTextColor(Color.rgb(112,128,144));
                        txt_numDoc.setTextColor(Color.rgb(112,128,144));
                        txt_tipoDoc.setText("O CPF gerado é");
                        txt_numDoc.setText(CPF);
                    }
                }
                else {
                    if (rdb_validar.isChecked() == true) {
                        if (String.valueOf(txt_documento.getText()).equals("")) {
                            txt_documento.requestFocus();
                            txt_documento.setError("Algo deu errado. Tente informar um valor");
                        }
                        else {
                            if (String.valueOf(txt_documento.getText()).length() < 14 || String.valueOf(txt_documento.getText()).length() > 14) {
                                Toast.makeText(MainActivity.this, "Um documento CNPJ contém 14 algarismos e foi informado " + String.valueOf(txt_documento.getText()).length() + ". Tente novamente!", Toast.LENGTH_LONG).show();
                            }
                            else {
                                GeraCpfCnpj validaCNPJ = new GeraCpfCnpj();
                                String CNPJ = String.valueOf(txt_documento.getText());
                                boolean result = validaCNPJ.isCNPJ(CNPJ);
                                if (result == true) {
                                    txt_tipoDoc.setTextColor(Color.rgb(0,128,0));
                                    txt_numDoc.setTextColor(Color.rgb(50,205,50));
                                    txt_result.setTextColor(Color.rgb(0,255,0));

                                    txt_tipoDoc.setText("CNPJ");
                                    txt_numDoc.setText(validaCNPJ.imprimeCNPJ(CNPJ));
                                    txt_result.setText("VÁLIDO");
                                }
                                else {
                                    //Toast.makeText(MainActivity.this, "O CNPJ " + validaCNPJ.imprimeCPF(CNPJ) + " é inválido!", Toast.LENGTH_LONG).show();
                                    txt_tipoDoc.setTextColor(Color.rgb(139,0,0));
                                    txt_numDoc.setTextColor(Color.rgb(165,42,42));
                                    txt_result.setTextColor(Color.rgb(255,0,0));

                                    txt_tipoDoc.setText("CNPJ");
                                    txt_numDoc.setText(validaCNPJ.imprimeCNPJ(CNPJ));
                                    txt_result.setText("INVÁLIDO");
                                }
                            }
                        }
                    }
                    else if (rdb_gerar.isChecked() == true) {
                        GeraCpfCnpj geraCNPJ = new GeraCpfCnpj();
                        String CNPJ = geraCNPJ.cnpj(true);

                        txt_tipoDoc.setTextColor(Color.rgb(112,128,144));
                        txt_numDoc.setTextColor(Color.rgb(112,128,144));
                        txt_tipoDoc.setText("O CNPJ gerado é");
                        txt_numDoc.setText(CNPJ);
                    }
                }
                EscondeTeclado(view);
            }

        });

    }

    private void Vibrar() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 60;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        vibrator.vibrate(milliseconds);
    }
    private void EscondeTeclado(View view){
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    }


}
