import React, { useState } from "react";

import { DataGrid, GridToolbarContainer, GridActionsCellItem } from "@mui/x-data-grid";
import {
  Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle,
  useTheme,
} from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import { Formik } from "formik";
import * as yup from "yup";

// TOTO -- Criar o OrdersFormInputs
// adicionar id, nome do produto e preço na lista
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import { Constants } from "../../data/constants";
import FormListCalls from "../../components/FormsListCalls";
import { tokens } from "../../theme";
import OrdersFormInputs from "../../components/formInputs/Orders";

const initialValues = {
  requiredDate: "", comments: "", customerId: "", productId: "",
  quantityOrdered: "", priceEach: "",
}

// TODO -- adicionar validação para comments, aceitar somente caracteres normais

const commentsRegex = /^[\p{L}\p{N}\s.,!?'"()-]+$/u;


const ordersSchema = yup.object().shape(({
  requiredDate: yup.date().required(),
  comments: yup
    .string()
    .matches(commentsRegex, "Accepts only text"),
  customerId: yup.number().required(),

  productId: yup.number().required(),
  quantityOrdered: yup.number().required(),
  priceEach: yup.number().required(),
}));

const GridActionTooltip = styled(({ className, ...props }) => (
  <Tooltip {...props} classes={{ popper: className }} />
))(() => ({
  [`& .${tooltipClasses.tooltip}`]: {
    backgroundColor: colors.primary[400],
    color: colors.primary[100],
    fontSize: 15,
    border: `1px solid ${colors.primary[100]}`,
    i
  },
}));

const columns = [
  { field: "orderId", headerName: "ORDER ID", flex: 0.5 },
  { field: "productId", headerName: "PRODUCT ID", flex: 0.5 },
  { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 0.5 },
  { field: "priceEach", headerName: "PRICE EACH", flex: 0.5 },
  { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
  {
    field: 'actions',
    type: 'actions',
    headerName: 'ACTIONS',
    width: 100,
    cellClassName: 'actions',
    getActions: (params) => {

      return [
        <GridActionTooltip title="Delete this Line"
          placement="bottom">
          <GridActionsCellItem
            icon={<DeleteIcon />}
            label="Delete"
            onClick={handleDeleteDatagridButton(params)}
            color="inherit"
          />
        </GridActionTooltip>,
      ];
      t
    },
  },
]

const FormAddOrders = () => {

  const url = Urls();
  const [rows, setRows] = useState([]);
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  //
  // TODO -- Fazer ou verificar se tem um endpoint para listar os customers id e nome, e product id, name e msrp
  // const [dataIdName, setDataIdName] = useState(null);
  // FormListCalls(url.employees.findByIdNames, setDataIdName);
  const [dataProductIdNameMsrp, setDataProductIdNameMsrp] = useState(null);
  FormListCalls(url.products.findByIdNameMsrp, setDataProductIdNameMsrp);


  // TODO -- verifiquei que precisa criar o método POST para inserir na tabela OrderDetails

  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');

  // TODO -- Descobrir uma forma de fazer o submit para orders e ordersDetails
  // com os objetos que estiverem no form, se for um ou dois
  const handleFormSubmit = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);
    try {
      const response = await fetch(url.orders.findAll_Post, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus('Order created successfully!');
        setResetFormFn(() => resetForm);
      } else {
        setStatus(`Error: ${data.title || 'Failed to create Order'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create Order'}`);
    }

    setSubmitting(false);
    setDialogOpen(true);
  }

  // TODO -- fazer a verificação com o response de orders e orderDetails
  const handleClose = () => {
    setDialogOpen(false);
    if (responseCode === 201 && resetFormFn) {
      resetFormFn();
    }
  }

  const handleDeleteDatagridButton = (params) => () => {
    // TODO deletar a linha no dataGrid
    // setIdDelete(params.id);
    //
    // setDialogConfirmOpen(true);
  }

  return (

    <Box>
      <Box m="20px">
        <Header title="CREATE ORDER" subtitle="Create a new Order" />

        <Formik
          onSubmit={handleFormSubmit}
          initialValues={initialValues}
          validationSchema={ordersSchema}
        >

          {({ values, errors, touched, handleBlur, handleChange, handleSubmit,
            setFieldValue }) => (
            <form onSubmit={handleSubmit}>
              <Box
                display="grid"
                gap="20px"
                gridTemplateColumns="repeat(4, minmax(0, 1fr))"
                sx={{
                  "& > div": { gridColumn: isNonMobile ? undefined : "span 4" },
                }}
              >

                <OrdersFormInputs
                  handleBlur={handleBlur} handleChange={handleChange}
                  values={values} touched={touched} errors={errors}
                  setFieldValue={setFieldValue} dataProductIdNameMsrp={dataProductIdNameMsrp}
                />

              </Box>
              <Box display="flex" justifyContent="end" mt="20px">
                <Button type="submit" color="secondary" variant="contained">
                  Create New Employee
                </Button>
              </Box>

              <Dialog open={dialogOpen} onClose={handleClose}>
                <DialogTitle>Operation Status</DialogTitle>
                <DialogContent>
                  <DialogContentText>
                    {status}
                    {responseCode !== null && <br />}
                    Response Code: {responseCode}
                  </DialogContentText>
                  <DialogActions>
                    <Button onClick={handleClose} color="primary">
                      OK
                    </Button>
                  </DialogActions>
                </DialogContent>
              </Dialog>
            </form>
          )}
        </Formik>

      </Box>


      <Box m="20px">
        <Box
          m="40px 0 0 0"
          height="75vh"
          sx={{
            "& .MuiDataGrid-root": {
              border: "none",
            },
            "& .MuiDataGrid-cell": {
              borderBottom: "none",
            },
            "& .name-column--cell": {
              color: colors.greenAccent[300],
            },
            "& .MuiDataGrid-columnHeader": {
              backgroundColor: colors.blueAccent[700],
              borderBottom: "none",
              fontSize: 14
            },
            "& .MuiDataGrid-virtualScroller": {
              backgroundColor: colors.primary[400],
            },
            "& .MuiDataGrid-footerContainer": {
              borderTop: "none",
              backgroundColor: colors.blueAccent[700],
            },
            "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
              color: `${colors.grey[100]} !important`,
            },
          }}
        >
          <DataGrid
            rows={rows}
            columns={columns}
          />
        </Box>


      </Box>
    </Box>
  )


}

export default FormAddOrders;
