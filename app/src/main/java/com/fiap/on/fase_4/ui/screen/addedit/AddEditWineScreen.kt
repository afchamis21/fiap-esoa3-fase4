package com.fiap.on.fase_4.ui.screen.addedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fiap.on.fase_4.R
import com.fiap.on.fase_4.data.model.Wine
import com.fiap.on.fase_4.ui.viewmodel.WineViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditWineScreen(
    navController: NavController,
    viewModel: WineViewModel,
    wineId: Int?
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var currentWine by remember { mutableStateOf<Wine?>(null) }
    val isEditing = wineId != null
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = wineId) {
        if (isEditing) {
            viewModel.getWineById(wineId!!).collect { wine ->
                if (wine != null) {
                    currentWine = wine
                    name = wine.name
                    price = wine.price.toString()
                    description = wine.description
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    val titleRes = if (isEditing) R.string.add_edit_wine_edit_title else R.string.add_edit_wine_add_title
                    Text(stringResource(id = titleRes))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back_action))
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val priceAsDouble = price.replace(',', '.').toDoubleOrNull()

                        if (priceAsDouble == null) {
                            scope.launch {
                                snackbarHostState.showSnackbar(context.getString(R.string.invalid_price))
                            }
                            return@IconButton
                        }

                        if (name.isNotBlank() && description.isNotBlank()) {
                            val wineToSave = currentWine?.copy(name = name, price = priceAsDouble, description = description)
                                ?: Wine(name = name, price = priceAsDouble, description = description)

                            if (isEditing) {
                                viewModel.update(wineToSave)
                            } else {
                                viewModel.insert(wineToSave)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Check, contentDescription = stringResource(id = R.string.save_action))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(id = R.string.wine_name_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text(stringResource(id = R.string.price_label)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(id = R.string.description_label)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
        }
    }
}
