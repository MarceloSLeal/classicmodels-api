import React, { useEffect, useState, useRef } from "react";

import { DataGrid } from "@mui/x-data-grid";
import { Box, Button, useTheme } from "@mui/material";
import useMediaQuery from "@mui/material/useMediaQuery";

import { Formik } from "formik";
import * as yup from "yup";

import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import FormListCalls from "../../components/FormsListCalls";
import { tokens } from "../../theme";
import PaymentsAddFormInputs from "../../components/formInputs/PaymentsAdd";
import useFetchData from '../../api/getData';
import dayjs from 'dayjs';
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"
import usePostForms from "../../components/formsRequests/PostForms";

const initialValues = {
  orderId: "",
  paymentDate: dayjs(),
  amount: 0,
};

const paymentsSchema = yup.object().shape({
  orderId: yup.number().required(),
  paymentDate: yup
    .mixed()
    .test("is-dayjs", "Date is not valid", (value) => dayjs.isDayjs(value))
    .required("Payment Date is required"),

  amount: yup.number().required(),
});

const FormAddPayments = () => {

  const url = Urls();
  const [rows, setRows] = useState([]);
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const isNonMobile = useMediaQuery("(min-width:600px)");
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const [urlSelectState, setUrlSelectState] = useState(null);
  const urlSelect = urlSelectState ? Urls(urlSelectState) : null;
  const { data, loading } = useFetchData(urlSelect ? urlSelect.orderdetails.findByOrderId : null);
  const [dataOrdersIdStatus, setDataOrdersIdStatus] = useState(null);
  const amountFormikValuesRef = useRef(null);
  const setAmountFieldValueRef = useRef(null);
  const { fetchPost } = usePostForms();

  const [updateSelect, setUpdateSelect] = useState(false);

  useEffect(() => {
    FormListCalls(url.orders.findByIdStatus, setDataOrdersIdStatus);
  }, [])


  const columns = [
    { field: "orderId", headerName: "ORDER ID", flex: 0.5 },
    { field: "productId", headerName: "PRODUCT ID", flex: 1 },
    { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 1 },
    { field: "priceEach", headerName: "PRICE EACH", flex: 1 },
    { field: "subtotal", headerName: "SUBTOTAL", flex: 1 },
    { field: "total", headerName: "TOTAL", flex: 1 },
    { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
  ]

  const handleSubmitPayments = async (values, { setSubmitting, resetForm }) => {
    setStatus('');
    setResponseCode(null);

    try {
      const valuesToSubmit = {
        ...values,
        paymentDate: dayjs().add(1, 'minute').format('YYYY-MM-DDTHH:mm:ssZ'),
        amount: parseFloat(values.amount),
      }

      const response = await fetchPost(valuesToSubmit, url.payments.findAll_Post);

      if (response.ok === false) {
        setStatus(`Error: ${response.status} - ${response.message}`);
        setDialogOpen(true);
        return;
      }

      setResponseCode(response.status);

      if (response.status === 201) {
        setStatus('Payment created successfully!');

        resetForm();
        setRows(null);
        setUpdateSelect(true);

      } else {
        setStatus(`Error: ${response.title || 'Failed to create Payment'} - ${response.detail || ''}`);
      }
    } catch (error) {
      setStatus(`Error: ${error || 'Failed to create Payment'}`);
    }
    setSubmitting(false);
    setDialogOpen(true);
  }

  const handleSelectOption = (selectedValue) => {
    setUrlSelectState(selectedValue);
  }

  const handleClose = () => {
    setDialogOpen(false);
  }

  useEffect(() => {
    if (data) {
      let cumulativeTotal = 0;

      const { orderId, orderList } = data;

      const newRows = orderList.map((item) => {
        const subtotal = item.quantityOrdered * item.priceEach;
        cumulativeTotal += subtotal;

        return {
          ...item,
          orderId: orderId,
          subtotal: subtotal.toFixed(2),
          total: cumulativeTotal.toFixed(2),
        };
      });

      setRows(newRows);

      if (setAmountFieldValueRef.current) {
        setAmountFieldValueRef.current("amount", cumulativeTotal.toFixed(2));
      }
    }
  }, [data]);

  useEffect(() => {
    if (updateSelect) {
      const updateSelectOptions = async () => {
        await FormListCalls(url.orders.findByIdStatus, setDataOrdersIdStatus);
        setUpdateSelect(false);
      };

      updateSelectOptions();
    }
  }, [updateSelect]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="CREATE PAYMENT" subtitle="Create a new Payment" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>
    )
  }

  return (
    <Box>
      <Box m="20px">
        <Header title="CREATE PAYMENT" subtitle="Create a new Payment" />

        <Formik
          onSubmit={handleSubmitPayments}
          initialValues={initialValues}
          validationSchema={paymentsSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

            useEffect(() => {
              amountFormikValuesRef.current = values;
              setAmountFieldValueRef.current = setFieldValue;
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

                  <PaymentsAddFormInputs
                    handleBlur={handleBlur} handleChange={handleChange} values={values}
                    touched={touched} errors={errors} paymentsSchema={paymentsSchema}
                    setFieldValue={setFieldValue} dataOrdersIdStatus={dataOrdersIdStatus}
                    handleSelectOption={handleSelectOption}
                  />

                  <Button type="submit" color="secondary" variant="contained"
                    sx={{ gridColumn: "span 1" }}>
                    Create New Payment
                  </Button>

                </Box>
              </form>
            )
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
            getRowId={(row) => `${row.orderId}-${row.productId}`}
          />
        </Box>
      </Box>

      <OperationStatusDialog
        dialogOpen={dialogOpen} onClose={handleClose} status={status}
        responseCode={responseCode} onClick={handleClose}
      />

    </Box>
  )

}

export default FormAddPayments;
