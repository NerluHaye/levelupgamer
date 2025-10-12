package com.example.levelupgamer.ui

@Composable
fun PantallaPrincipal(modifier: Modifier,viewModel: EstadoViewModel = viewModel()){

    val estado = viewModel.activo.collectAsState()
    val mostrarMensaje= viewModel.mostrarMensaje.collectAsState()



    if(estado.value== null){
        Box(
            modifier=modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
            //LinearProgressIndicator()

        }
    }else{
        val estadoActivo= estado.value!!
        val colorAnimado by animateColorAsState(
            targetValue = if(estadoActivo) Color(0xFF4CAF50) else Color(0xFFB0BEC5),
            animationSpec = tween(durationMillis = 500),
            label = ""
        )


        val textoBoton by remember (estadoActivo) {
            derivedStateOf {  if (estadoActivo) "Desactivar" else "Activar"}
        }


        Column (
            modifier = modifier.
            fillMaxSize().
            padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = {viewModel.alternarEstado()},
                colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
                modifier = Modifier.height(50.dp)
            ) {
                Text(textoBoton, style = MaterialTheme.typography.titleLarge)
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = mostrarMensaje.value!!) {
                Text(
                    text="¡Estado Guardado Exitosamente!",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}