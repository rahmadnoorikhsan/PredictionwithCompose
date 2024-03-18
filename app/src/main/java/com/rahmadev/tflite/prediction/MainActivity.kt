package com.rahmadev.tflite.prediction

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rahmadev.tflite.prediction.ui.theme.PredictionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PredictionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var value by rememberSaveable {
                        mutableStateOf("")
                    }
                    var result by rememberSaveable {
                        mutableStateOf("Hasil")
                    }

                    val predictionHelper = PredictionHelper(
                        context = this,
                        onResult = {
                            result = it
                        },
                        onError = {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }
                    )

                    PredictRiceScreen(
                        value = value,
                        result = result,
                        onValueChange = { newValue ->
                            value = newValue
                        },
                        onPredict = {
                            predictionHelper.predict(value)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PredictRiceScreen(
    value: String,
    result: String,
    onValueChange: (String) -> Unit,
    onPredict: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.rice_stock_predictor),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.sales_this_month))
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPredict) {
            Text(text = stringResource(id = R.string.predict))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = result)
    }
}