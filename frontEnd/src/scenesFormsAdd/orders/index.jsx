import React, { useState, useEffect } from "react";

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

import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import { tokens } from "../../theme";
import OrdersFormInputs from "../../components/formInputs/Orders";


const initialValues = {
  requiredDate: "", comments: "", customerId: "", productId: "",
  quantityOrdered: "", priceEach: "",
}

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

let lineCounter = 0;

const FormAddOrders = () => {

  const url = Urls();
  const [rows, setRows] = useState([]);
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [resetFormFn, setResetFormFn] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const [setFieldValueRef, setSetFieldValueRef] = useState(null);
  const [errorsRef, setErrorsRef] = useState(null);

  // TODO -- verifiquei que precisa criar o método POST para inserir na tabela OrderDetails

  const [dataProductIdNameQuantityInStock, setDataProductIdNameQuantityInStock] = useState(null);
  FormListCalls(url.products.findByIdNameQuantityInStock, setDataProductIdNameQuantityInStock);

  const [dataCustomersIdNameCreditLimit, setDataCustomersIdNameCreditLimit] = useState(null);
  FormListCalls(url.customers.findByIdNameCreditLimit, setDataCustomersIdNameCreditLimit);


  const GridActionTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} classes={{ popper: className }} />
  ))(() => ({
    [`& .${tooltipClasses.tooltip}`]: {
      backgroundColor: colors.primary[400],
      color: colors.primary[100],
      fontSize: 15,
      border: `1px solid ${colors.primary[100]}`,
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
      },
    },
  ]

  const [formValues, setFormValues] = useState({
    orderId: null,
    productId: null,
    quantityOrdered: null,
    priceEach: null,
    orderLineNumber: null,
  })

  const resetFormValues = () => {
    setFormValues({
      orderId: null,
      productId: null,
      quantityOrdered: null,
      priceEach: null,
      orderLineNumber: null,
    })
  }

  const handleInputChange = (name, value) => {
    setFormValues((prevValues) => ({
      ...prevValues,
      [name]: value,
    }));
  };

  /////////////////////////////////////////////////////////////////////////////
  // let lineCounter = 0;

  const createRow = () => {

    lineCounter += 1;
    const msrpValue = dataProductIdNameQuantityInStock?.find(product =>
      product.id === formValues.productId)?.msrp;
    formValues.priceEach = msrpValue;
    formValues.orderLineNumber = lineCounter;

    setFieldValueRef("quantityOrdered", "", false);
    setFieldValueRef("productId", "", false);

    let addRow = {
      orderId: null, productId: formValues.productId,
      quantityOrdered: formValues.quantityOrdered, priceEach: formValues.priceEach,
      orderLineNumber: lineCounter,
    };

    setRows((prevRows) => [...prevRows, addRow]);
    resetFormValues();

  };

  const UpdateRowsProp = () => {
    const [rows, setRows] = React.useState(() => [
      createRow(),
    ]);
  }

  const handleUpdateRow = () => {
    if (rows.length === 0) {
      return;
    }
    setRows((prevRows) => {
      const rowToUpdateIndex = randomInt(0, rows.length - 1);

      return prevRows.map((row, index) =>
        index === rowToUpdateIndex ? { ...row, username: randomUserName() } : row,
      );
    });
  };

  const handleUpdateAllRows = () => {
    setRows(rows.map((row) => ({ ...row, username: randomUserName() })));
  };

  const handleDeleteRow = () => {
    if (rows.length === 0) {
      return;
    }
    setRows((prevRows) => {
      const rowToDeleteIndex = randomInt(0, prevRows.length - 1);
      return [
        ...rows.slice(0, rowToDeleteIndex),
        ...rows.slice(rowToDeleteIndex + 1),
      ];
    });
  };

  const handleTeste = () => {
    console.log("formValues", formValues);
    console.log("rows", rows);
    console.log("errors!!", !!errorsRef.quantityOrdered);
  }

  const handleAddRow = () => {

    if (!!errorsRef.quantityOrdered === true) {
      setStatus("Product Id or Quantity has an error");
      setDialogOpen(true);
      return
    }

    if (formValues.productId === null || formValues.quantityOrdered === null) {
      setStatus("Product ID or Quantity is empty");
      setDialogOpen(true);
      return
    }

    const isProdId = rows?.some(prod => prod.productId === formValues.productId);

    if (isProdId) {
      setStatus("The product is already on order");
      setDialogOpen(true);
      return
    }

    // TODO -- verificar se prodId e quantityOrdered estão vazios

    // setRows((prevRows) => [...prevRows, createRow()]);
    createRow();
  };

  /////////////////////////////////////////////////////////////////////////////

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
    const updatedRows = rows.filter(row => row.orderLineNumber !== params.row.orderLineNumber);
    const reorderedRows = updatedRows.map((row, index) => ({
      ...row,
      orderLineNumber: index + 1, // Reorganizar para que a sequência seja contínua
    }));
    setRows(reorderedRows);
  }

  return (

    <Box>
      <Box m="20px">
        <Header title="CREATE ORDER" />

        <Formik
          onSubmit={handleFormSubmit}
          initialValues={initialValues}
          validationSchema={ordersSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

            useEffect(() => {
              setSetFieldValueRef(() => setFieldValue);
              setErrorsRef(() => errors);
            }, [setFieldValue, errors, touched]);

            return (
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
                    ordersSchema={ordersSchema} setFieldValue={setFieldValue}
                    dataProductIdNameQuantityInStock={dataProductIdNameQuantityInStock}
                    dataCustomersIdNameCreditLimit={dataCustomersIdNameCreditLimit}
                    handleInputChange={handleInputChange}
                  />

                  <Button onClick={handleAddRow} color="secondary" variant="contained"
                    sx={{ gridColumn: "span 1", width: "50%" }}>
                    Add New Item
                  </Button>

                  <Button onClick={handleTeste} color="secondary" variant="contained"
                    sx={{ gridColumn: "span 1", width: "50%" }}>
                    Teste
                  </Button>
                </Box>

                <Dialog open={dialogOpen} onClose={handleClose}>
                  <DialogTitle>Operation Status</DialogTitle>
                  <DialogContent>
                    <DialogContentText>
                      {status}
                    </DialogContentText>
                    <DialogActions>
                      <Button onClick={handleClose} color="primary">
                        OK
                      </Button>
                    </DialogActions>
                  </DialogContent>
                </Dialog>
              </form>
            );
          }}
        </Formik>
      </Box>

      <Box m="20px">
        <Box
          m="40px 0 0 0"
          height="60vh"
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
              fontSize: 12,
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
            getRowId={(row) => `${row.orderLineNumber}`}
          />
        </Box>

      </Box>
    </Box>
  )


}

export default FormAddOrders;
