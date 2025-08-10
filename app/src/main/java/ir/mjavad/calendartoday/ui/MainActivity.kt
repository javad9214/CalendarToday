package ir.mjavad.calendartoday.ui


// Enhanced MainActivity with Persian strings and theme
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.mjavad.calendartoday.R
import ir.mjavad.calendartoday.util.FarsiDateUtil.getTodayFormatted

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersianWidgetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WidgetHelperScreen()
                }
            }
        }
    }

    @Composable
    fun PersianWidgetTheme(content: @Composable () -> Unit) {
        val colorScheme = lightColorScheme(
            primary = Color(0xFF1976D2),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE3F2FD),
            onPrimaryContainer = Color(0xFF0D47A1),
            secondary = Color(0xFF03DAC6),
            onSecondary = Color.Black,
            secondaryContainer = Color(0xFFE0F7FA),
            onSecondaryContainer = Color(0xFF006064),
            background = Color(0xFFF8F9FA),
            onBackground = Color(0xFF1A1A1A),
            surface = Color.White,
            onSurface = Color(0xFF1A1A1A),
            surfaceVariant = Color(0xFFF5F5F5),
            onSurfaceVariant = Color(0xFF666666)
        )

        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }

    @Composable
    fun WidgetHelperScreen() {
        var showInstructions by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.persian_date_widget),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = getTodayFormatted(),
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_widget_to_home),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Button(
                        onClick = {
                            requestAddWidget()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(stringResource(R.string.request_widget_addition))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = {
                            showInstructions = !showInstructions
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (showInstructions)
                                stringResource(R.string.hide_instructions)
                            else
                                stringResource(R.string.show_manual_instructions)
                        )
                    }
                }
            }

            if (showInstructions) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.manual_widget_steps),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        val steps = listOf(
                            stringResource(R.string.step_1),
                            stringResource(R.string.step_2),
                            stringResource(R.string.step_3),
                            stringResource(R.string.step_4),
                            stringResource(R.string.step_5)
                        )

                        steps.forEach { step ->
                            Text(
                                text = step,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.security_note),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    private fun requestAddWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val myProvider = ComponentName(this, BlurredTextWidgetReceiver::class.java)

        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            // This will show a system dialog asking user to confirm widget addition
            val success = appWidgetManager.requestPinAppWidget(myProvider, null, null)

            if (success) {
                Toast.makeText(this, getString(R.string.widget_addition_requested), Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.widget_addition_failed), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.widget_pinning_not_supported), Toast.LENGTH_LONG).show()
            showManualInstructions()
        }
    }

    private fun showManualInstructions() {
        Toast.makeText(
            this,
            getString(R.string.manual_instruction_toast),
            Toast.LENGTH_LONG
        ).show()
    }
}