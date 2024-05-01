package edu.okei.reward.teachers.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import edu.okei.core.domain.model.teacher.TeacherModel
import edu.okei.reward.R
import edu.okei.reward.common.ui.edit_text.EditText
import edu.okei.reward.common.ui.theme.dimensions
import edu.okei.reward.common.ui.user.getStringRole
import edu.okei.reward.teachers.model.TeachersState

@Composable
fun TeacherManagementContent(
    state: TeachersState.TeacherManagement
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        EditText(
            value = state.searchText,
            onValueChange = {},
            hint = stringResource(R.string.search),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.dimensions.grid_5_5
                ),
            textStyle = MaterialTheme.typography.body2
                .copy(
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                top = MaterialTheme.dimensions.grid_5_5,
                start = MaterialTheme.dimensions.grid_4,
                end = MaterialTheme.dimensions.grid_4,
                bottom = WindowInsets.navigationBars
                    .asPaddingValues().calculateBottomPadding() +
                        MaterialTheme.dimensions.grid_5_5
            ),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimensions.grid_3_5)
        ){
            items(state.listTeacher.size){index ->
               TeacherItem(model = state.listTeacher[index])
            }
        }
    }
}

@Composable
private fun TeacherItem(model: TeacherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                MaterialTheme.dimensions.borderSmall,
                MaterialTheme.colors.primary,
                MaterialTheme.shapes.medium
            )
            .padding(
                vertical = MaterialTheme.dimensions.grid_4_5,
                horizontal = MaterialTheme.dimensions.grid_5_5 * 1.5f
            ),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.dimensions.grid_2)
    ) {
        Text(
            text = model.fullname,
            style = MaterialTheme.typography.h5
                .copy(color = MaterialTheme.colors.primary),
        )
        Text(
            text = "${stringResource(R.string.user_role)} ${getStringRole(model.role)}",
            style = MaterialTheme.typography.caption
                .copy(color = MaterialTheme.colors.primary),
        )
    }
}