package com.pedro.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pedro.calculadora.ui.theme.CalculadoraTheme
import com.pedro.calculadora.ui.theme.TemaDoAPP

class MainActivity : ComponentActivity() {
    var visor by mutableStateOf("0")
    val pilhaOperador = mutableListOf<String>()
    val pilhaOperando = mutableListOf<String>()

    var aguardandoOperando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var temaSelecionado by remember {
                mutableStateOf(TemaDoAPP.CLARO)
            }

            CalculadoraTheme(temaSelecionado) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        SeletorDeTemas(temaSelecionado = temaSelecionado,
                            onTemaSelecionado = { tema ->
                                temaSelecionado = tema
                            })
                    }
                    criaCalculadora(visor)
                }
            }
        }
    }
    @Composable
    fun criaCalculadora(visor:String){
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                fontSize = 32.sp,
                text = visor
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("%", BotaoOperacao.PERCENTUAL)
                criaBotaoPequeno("/", BotaoOperacao.DIVISAO)
                criaBotaoPequeno("*", BotaoOperacao.MULTIPLICACAO)
                criaBotaoPequeno("-", BotaoOperacao.SUBTRACAO)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("Sin", BotaoOperacao.SENO)
                criaBotaoPequeno("Cos", BotaoOperacao.COSSENO)
                criaBotaoPequeno("Tan", BotaoOperacao.TANGENTE)
                criaBotaoPequeno("Pi", BotaoOperacao.PI)
                }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("Sqrt", BotaoOperacao.RAIZ)
                criaBotaoPequeno("^", BotaoOperacao.POTENCIA)
                criaBotaoPequeno("!", BotaoOperacao.FATORIAL)
                criaBotaoPequeno("Inv", BotaoOperacao.INVERSO)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("7", BotaoOperacao.SETE)
                criaBotaoPequeno("8", BotaoOperacao.OITO)
                criaBotaoPequeno("9", BotaoOperacao.NOVE)
                criaBotaoPequeno("+", BotaoOperacao.SOMA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("4", BotaoOperacao.QUATRO)
                criaBotaoPequeno("5", BotaoOperacao.CINCO)
                criaBotaoPequeno("6", BotaoOperacao.SEIS)
                criaBotaoPequeno(".", BotaoOperacao.VIRGULA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("1", BotaoOperacao.UM)
                criaBotaoPequeno("2", BotaoOperacao.DOIS)
                criaBotaoPequeno("3", BotaoOperacao.TRES)
                criaBotaoPequeno("=", BotaoOperacao.IGUALDADE)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("+/-", BotaoOperacao.INVERTE)
                criaBotaoPequeno("0", BotaoOperacao.ZERO)
                criaBotaoPequeno("C", BotaoOperacao.LIMPAR)
                criaBotaoPequeno("<-", BotaoOperacao.BACK)
            }
            }
        }
    @Composable
    fun criaBotaoPequeno(texto: String, identificador: BotaoOperacao){
        Button(
            modifier = Modifier.width(80.dp).height(50.dp),
            onClick = {
                if (identificador.ordinal <= BotaoOperacao.PI.ordinal){
                    numPress(identificador)
                } else {
                    opPress(identificador)
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = if(identificador == BotaoOperacao.IGUALDADE) {
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            } else{
                ButtonDefaults.buttonColors()
            }
        ) {
            Text(
                texto,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    fun numPress(identificador: BotaoOperacao){
        if (aguardandoOperando){
            visor = "0"
            aguardandoOperando = false
        }
        if ((visor == "0")&&(identificador.name == BotaoOperacao.ZERO.name)){return}

        if((visor.contains(",")) &&(identificador.name == BotaoOperacao.VIRGULA.name) ){return}

        var tmp = identificador.name
        if(tmp == BotaoOperacao.VIRGULA.name){
            tmp = ","
        } else if (tmp == BotaoOperacao.PI.name){
            tmp = Math.PI.toString()
        } else {
            tmp = identificador.ordinal.toString()
        }

        if (visor.length == 1 && visor == "0"){
            visor = tmp
        } else {
            visor += tmp
        }
    }

    fun opPress(identificador: BotaoOperacao){
        if(identificador == BotaoOperacao.LIMPAR){
            pilhaOperador.clear()
            pilhaOperando.clear()
            aguardandoOperando = false
            visor = "0"
            return
        }

        if (identificador == BotaoOperacao.SENO){
            visor = (Math.sin(Math.toRadians(visor.toDouble()))).toString()
            return
        }

        if (identificador == BotaoOperacao.COSSENO){
            visor = (Math.cos(Math.toRadians(visor.toDouble()))).toString()
            return
        }

        if (identificador == BotaoOperacao.TANGENTE){
            visor = (Math.tan(Math.toRadians(visor.toDouble()))).toString()
            return
        }

        if (identificador == BotaoOperacao.FATORIAL){
            if(visor.replace(',','.').toDouble()<0||visor.contains(',')){
                visor = "Entrada Inválida"
                return
            }

            var valor = visor.toInt()
            var calc = 1
            for (i in 2..valor) {
                calc *= i
            }
            visor = calc.toString()
            return

        }

        if (identificador == BotaoOperacao.RAIZ){
            if (visor.toDouble()<0){
                visor = "Entrada Inválida"
                return
            }
            visor = (Math.sqrt(visor.toDouble())).toString()
            return

        }

        if (identificador == BotaoOperacao.INVERSO){
            if (visor.toInt()==0){
                visor = "Entrada Inválida"
                return
            }
            visor = (1/visor.toFloat()).toString()
            return
        }

        if (identificador == BotaoOperacao.INVERTE){
            visor = (visor.toDouble() * (-1.0)).toString()
            return
        }

        if (identificador == BotaoOperacao.BACK){
            visor = visor.dropLast(1)
            return
        }

        if (identificador == BotaoOperacao.IGUALDADE){
            igualdade()
            return
        }

        if (aguardandoOperando){
            if (pilhaOperador.isNotEmpty()){
                pilhaOperador[pilhaOperador.lastIndex] = identificador.name
            }
            return
        }

        if (pilhaOperador.isEmpty()){
            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)
        } else {
            //executar a operação
            igualdade()
            pilhaOperador.add(identificador.name)
            pilhaOperando.add(visor)
        }
        aguardandoOperando = true
    }

    fun igualdade(){
        if (pilhaOperador.isEmpty() || pilhaOperando.isEmpty()){
            return
        }
        val operador = pilhaOperador.removeAt(pilhaOperador.lastIndex)
        val operando = pilhaOperando.removeAt(pilhaOperando.lastIndex).toFloat()
        val aux = visor.toFloat()

        //implementar as operações
        if(operador == BotaoOperacao.SOMA.name) {
            visor = (operando + aux).toString()
        } else if (operador == BotaoOperacao.SUBTRACAO.name){
            visor = (operando - aux).toString()
        } else if (operador == BotaoOperacao.MULTIPLICACAO.name){
            visor = (operando * aux).toString()
        } else if (operador == BotaoOperacao.DIVISAO.name){
            if (aux == 0.toFloat() && operando == aux){
                visor = "Resultado Indefinido"
            } else if (aux == 0.toFloat()) {
                visor = "Não é possível dividir por zero"
            } else {
            visor = (operando / aux).toString()
            }
        } else if (operador == BotaoOperacao.PERCENTUAL.name){
            visor = (operando * (aux/100)).toString()
        } else if (operador == BotaoOperacao.POTENCIA.name){
            visor = (Math.pow(operando.toDouble(),aux.toDouble())).toString()
        }
        aguardandoOperando = true
    }

    enum class BotaoOperacao {
        ZERO,
        UM,
        DOIS,
        TRES,
        QUATRO,
        CINCO,
        SEIS,
        SETE,
        OITO,
        NOVE,
        VIRGULA,
        PI,
        SOMA,
        SUBTRACAO,
        IGUALDADE,
        LIMPAR,
        MULTIPLICACAO,
        DIVISAO,
        PERCENTUAL,
        SENO,
        COSSENO,
        TANGENTE,
        FATORIAL,
        INVERSO,
        RAIZ,
        POTENCIA,
        INVERTE,
        BACK
    }

    @Composable
    fun SeletorDeTemas(temaSelecionado : TemaDoAPP,
                       onTemaSelecionado: (TemaDoAPP) -> Unit){
        var expandido by remember {
            mutableStateOf(false)
        }

        Box(modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.CenterEnd){
            OutlinedButton(onClick = {
                expandido = true
            }) {
                Text(
                    when(temaSelecionado){
                        TemaDoAPP.CLARO -> "Tema Claro"
                        TemaDoAPP.ESCURO -> "Tema Escuro"
                        TemaDoAPP.DINAMICO -> "Tema Dinâmico"
                        TemaDoAPP.MEDIEVAL -> "Tema Medieval"
                    }
                )
            }
            DropdownMenu(expanded = expandido,
                onDismissRequest = {
                    expandido = false
                }) {
                DropdownMenuItem(
                    text = {Text("Tema Claro")},
                    onClick = {
                        onTemaSelecionado(TemaDoAPP.CLARO)
                    }

                )
                DropdownMenuItem(
                    text = { Text("Tema Escuro") },
                    onClick = {
                        onTemaSelecionado(TemaDoAPP.ESCURO)
                        expandido = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Tema Dinâmico") },
                    onClick = {
                        onTemaSelecionado(TemaDoAPP.DINAMICO)
                        expandido = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Tema Medieval") },
                    onClick = {
                        onTemaSelecionado(TemaDoAPP.MEDIEVAL)
                        expandido = false
                    }
                )
            }
        }
    }
}
