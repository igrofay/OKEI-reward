package edu.okei.reward.teachers.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import edu.okei.reward.R
import edu.okei.reward.common.ui.button.CustomButton
import edu.okei.reward.common.ui.edit_text.EditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.teachers.model.TeacherAddOrEditEvent
import edu.okei.reward.teachers.view_model.TeacherAddOrEditVM
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun TeacherAddOrEditDialog(
    onDismissRequest: () -> Unit
) {
    val addOrEditVM by rememberVM<TeacherAddOrEditVM>()
    val state by addOrEditVM.collectAsState()
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.dimensions.grid_3_5,
                        vertical = MaterialTheme.dimensions.grid_4_5
                    )
            ) {
                Text(
                    text = stringResource(R.string.adding_new_teacher),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                        .copy(color = MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_5))
                EditText(
                    value = state.fio,
                    onValueChange = {
                        addOrEditVM.onEvent(TeacherAddOrEditEvent.InputFIO(it))
                    },
                    hint = stringResource(R.string.fio)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_3_5))
                CustomButton(
                    text = stringResource(R.string.add),
                ) {
                    addOrEditVM.onEvent(TeacherAddOrEditEvent.AddOrEdit)
                }
            }
        }
    }
}