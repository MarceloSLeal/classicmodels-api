import React, { useEffect, useState, useRef } from "react";

import { DataGrid, GridActionsCellItem } from "@mui/x-data-grid";
import { Box, Button, useTheme } from "@mui/material";
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
import OrdersAddFormInputs from "../../components/formInputs/OrdersAdd";
import OrdersDetailsFormInputs from "../../components/formInputs/OrdersDetails";
import dayjs from 'dayjs';
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const tomorrow = dayjs().add(1, 'day');
const ordersInitialValues = {
  customerId: "",
  requiredDate: { date: tomorrow },
  comments: "",
};
const ordersDetailsInitialValues = {
  productId: "", quantityOrdered: "", priceEach: ""
}

const commentsRegex = /^[\p{L}\p{N}\s.,!?'"()-]+$/u;
const ordersSchema = yup.object().shape({
  customerId: yup.number().required(),
  requiredDate: yup.object().shape({
    date: yup.mixed().required("Required Date is required").test(
      "is-dayjs",
      "Date is not valid",
      value => dayjs.isDayjs(value)
    ),
  }),
  comments: yup
    .string()
    .matches(commentsRegex, "Accepts only text"),
});
const ordersDetailsSchema = yup.object().shape(({
  productId: yup.number().required(),
  quantityOrdered: yup.number().required(),
}));


const FormAddOrders = () => {

  const url = Urls();
  const [rows, setRows] = useState([]);
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const ordersFormikValuesRef = useRef(null);
  const setOrdersFieldValueRef = useRef(null);
  const [lineCounter, setLineCounter] = useState(0);

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

  const handleSubmitOrdersDetails = (values, { setSubmitting, resetForm }) => {

    const customerId = ordersFormikValuesRef.current?.customerId;

    if (customerId === "") {
      setStatus("Select a Customer first");
      setResponseCode(null);
      setDialogOpen(true);
      return
    }

    const isProdId = rows?.some(prod => prod.productId === values.productId);

    if (isProdId) {
      setStatus("The product is already on order");
      setDialogOpen(true);
      return
    }

    function calcDicount(numProd) {
      if (numProd == 1) return 0;
      if (numProd >= 100) return 40;

      const discount = ((numProd - 1) / 99) * 40;
      return discount.toFixed(2);
    }

    const msrpValue = dataProductIdNameQuantityInStock?.find(product =>
      product.id === values.productId)?.msrp;

    let discCalc = msrpValue - (msrpValue * calcDicount(values.quantityOrdered) / 100);
    values.priceEach = discCalc.toFixed(2);

    const totalFromRows = rows.reduce((total, row) => {
      return total + row.quantityOrdered * row.priceEach;
    }, 0);
    const total = totalFromRows + (values.quantityOrdered * values.priceEach);
    const checkCredit = dataCustomersIdNameCreditLimit.find((custCredit) => {
      return custCredit.id === customerId;
    });
    if (total > checkCredit.creditLimit) {
      setStatus(`With this last order the total amount exceeds the customer's 
      credit limit. ${total}`);
      setDialogOpen(true);
      return
    }

    const newCounter = lineCounter + 1;
    setLineCounter((prevCounter) => prevCounter + 1);
    values.orderLineNumber = newCounter;
    let addRow = {
      orderId: null, productId: values.productId,
      quantityOrdered: values.quantityOrdered, priceEach: values.priceEach,
      orderLineNumber: newCounter,
    };

    setRows((prevRows) => [...prevRows, addRow]);

    resetForm();
    setSubmitting(false);
  };

  const handleDeleteDatagridButton = (params) => () => {
    const updatedRows = rows.filter(row => row.orderLineNumber !== params.row.orderLineNumber);
    const reorderedRows = updatedRows.map((row, index) => ({
      ...row,
      orderLineNumber: index + 1,
    }));
    setRows(reorderedRows);
    setLineCounter((prevCounter) => prevCounter - 1);
  }

  // TODO -- Ao adicionar uma order definir o requiredDate com hora 00:00
  // TODO -- Padronizar formato de data na exibição no frontEnd, DD-MM-YYYY
  const handleSubmitOrders = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);

    if (rows.length === 0) {
      setStatus('need to add items to the order');
      setDialogOpen(true);
      return
    }

    const formattedValues = {
      ...values,
      requiredDate: values.requiredDate.date.format('YYYY-MM-DDTHH:mm:ssZ'),
    };

    console.log(values);

    try {
      const response = await fetch(url.orders.findAll_Post, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formattedValues),
      });
      const data = await response.json();

      setResponseCode(response.status);

      if (response.ok) {
        setStatus(`Order ${data.id} created successfully!`);

        const responseOrderId = data.id;

        const rowsUpdate = rows.map((row) => ({
          ...row,
          orderId: responseOrderId,
        }));
        setRows(rowsUpdate);

        await handlePostOrdersDetails(rowsUpdate);

        setRows([]);
        setLineCounter(0);
        resetForm();
      } else {
        setStatus(`Error: ${data.title || 'Failed to create Order'}`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to create Order'}`);
    }

    setDialogOpen(true);

    setSubmitting(false);
    setDialogOpen(true);
  }

  const handlePostOrdersDetails = async (rowsUpdate) => {

    try {
      const response = await fetch(url.orderdetails.Post, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(rowsUpdate),
      });

      if (response.ok) {
        setStatus((prevString) => prevString + " Order Details created sucessfully!");
      } else {
        setStatus((prevString) => prevString + " " + response);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to save order details'}`);
    }
  }

  const handleClose = () => {
    setDialogOpen(false);
  }

  return (
    <Box>
      <Box m="20px">
        <Header title="CREATE ORDER" />

        <Formik
          onSubmit={handleSubmitOrders}
          initialValues={ordersInitialValues}
          validationSchema={ordersSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

            useEffect(() => {
              ordersFormikValuesRef.current = values;
              setOrdersFieldValueRef.current = setFieldValue;
            }, [values, setFieldValue]);

            return (
              <form onSubmit={handleSubmit}>
                <Box
                  display="grid"
                  gap="20px"
                  gridTemplateColumns="repeat(5, minmax(0, 1fr))"
                  sx={{
                    "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
                  }}
                >
                  <OrdersAddFormInputs
                    handleBlur={handleBlur} handleChange={handleChange}
                    values={values} touched={touched} errors={errors}
                    ordersSchema={ordersSchema} setFieldValue={setFieldValue}
                    dataCustomersIdNameCreditLimit={dataCustomersIdNameCreditLimit}
                  />

                  <Button type="submit" color="secondary" variant="contained"
                    sx={{ gridColumn: "span 1", height: "50%" }}>
                    Create New Order
                  </Button>

                </Box>
              </form>
            )
          }}
        </Formik>

        <Formik
          onSubmit={handleSubmitOrdersDetails}
          initialValues={ordersDetailsInitialValues}
          validationSchema={ordersDetailsSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => (

            <form onSubmit={handleSubmit}>
              <Box
                display="grid"
                gap="20px"
                gridTemplateColumns="repeat(5, minmax(0, 1fr))"
                sx={{
                  "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
                }}
              >
                <OrdersDetailsFormInputs
                  handleBlur={handleBlur} handleChange={handleChange} values={values} touched={touched}
                  errors={errors} ordersDetailsSchema={ordersDetailsSchema}
                  setFieldValue={setFieldValue} dataProductIdNameQuantityInStock={dataProductIdNameQuantityInStock}
                />

                <Button type="submit" color="secondary" variant="contained"
                  sx={{ gridColumn: "span 1" }}>
                  Add New Item
                </Button>
              </Box>

            </form>
          )}
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

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box >
  )
}

export default FormAddOrders;
