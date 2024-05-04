package edu.okei.reward.teachers.view

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import edu.okei.reward.R
import edu.okei.reward.common.ui.button.CustomButton
import edu.okei.reward.common.ui.edit_text.OutlinedEditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.view_model.rememberVM
import edu.okei.reward.teachers.model.AddOrEditTeacherEvent
import edu.okei.reward.teachers.model.AddOrEditTeacherSideEffect
import edu.okei.reward.teachers.view_model.AddOrEditTeacherVM
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun AddOrEditTeacherDialog(
    onDismissRequest: () -> Unit,
) {
    val addOrEditVM by rememberVM<AddOrEditTeacherVM>()
    val state by addOrEditVM.collectAsState()
    val context = LocalContext.current
    addOrEditVM.collectSideEffect{sideEffect->
        when(sideEffect){
            AddOrEditTeacherSideEffect.EditsCompleted -> onDismissRequest()
            is AddOrEditTeacherSideEffect.ShowMessage -> Toast
                .makeText(context, sideEffect.message, Toast.LENGTH_SHORT)
                .show()
        }
    }
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
                OutlinedEditText(
                    value = state.fio,
                    onValueChange = {
                        addOrEditVM.onEvent(AddOrEditTeacherEvent.InputFIO(it))
                    },
                    hint = stringResource(R.string.fio)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.grid_3_5))
                CustomButton(
                    text = stringResource(R.string.add),
                ) {
                    addOrEditVM.onEvent(AddOrEditTeacherEvent.AddOrEditTeacher)
                }
            }
        }
    }
}