import React, { useEffect, useState, useRef } from "react";

import { DataGrid, GridActionsCellItem } from "@mui/x-data-grid";
import { Box, Button, Divider, useTheme } from "@mui/material";
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
import PaymentsAddFormInputs from "../../components/formInputs/PaymentsAdd";
import useFetchData from '../../api/getData';
import OrdersAddFormInputs from "../../components/formInputs/OrdersAdd";
import OrdersDetailsFormInputs from "../../components/formInputs/OrdersDetails";
import dayjs from 'dayjs';
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const initialValues = {
  orderId: "",
  paymentDate: dayjs(),
  amount: 0,
};

const paymentsSchema = yup.object().shape({
  orderId: yup.number().required(),
  paymentDate: yup.object().shape({
    date: yup.mixed().required("Payment Date is required").test(
      "is-dayjs",
      "Date is not valid",
      value => dayjs.isDayjs(value)
    ),
  }),
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
  const ordersFormikValuesRef = useRef(null);
  const setOrdersFieldValueRef = useRef(null);
  const [lineCounter, setLineCounter] = useState(0);

  const [urlSelectState, setUrlSelectState] = useState(null);

  const urlSelect = Urls(urlSelectState);
  const { data, loading, error } = useFetchData(urlSelect.orderdetails.findByOrderId);

  const [dataOrdersIdStatus, setDataOrdersIdStatus] = useState(null);
  FormListCalls(url.orders.findByIdStatus, setDataOrdersIdStatus);

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
    { field: "productId", headerName: "PRODUCT ID", flex: 1 },
    { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 1 },
    { field: "priceEach", headerName: "PRICE EACH", flex: 1 },
    { field: "subtotal", headerName: "SUBTOTAL", flex: 1 },
    { field: "total", headerName: "TOTAL", flex: 1 },
    { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
  ]

  const handleSubmitPayments = () => {

  }

  // TODO -- função quer será chamada após selecionar um ítem no select
  const handleSelectOption = (selectedValue) => {

    setUrlSelectState(selectedValue);
    console.log(selectedValue);

    useEffect(() => {
      if (data) {

        let cumulativeTotal = 0;

        const { orderId, orderList } = data;

        const rows = orderList.map((item) => {
          const subtotal = item.quantityOrdered * item.priceEach;
          cumulativeTotal += subtotal;

          return {
            ...item,
            orderId: orderId,
            subtotal: subtotal.toFixed(2),
            total: cumulativeTotal.toFixed(2),
          };
        });

        setRows(rows);
      }
    }, [data]);

  }

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

  // if (error) {
  //   return (
  //     <Box m="20px">
  //       <Header title="CREATE PAYMENT" subtitle="Create a new Payment" />
  //       <Box>Error: {error.message}{ }</Box>
  //     </Box>
  //   );
  // }

  return (
    <Box>
      <Box m="20px">
        <Header title="CREATE PAYMENT" subtitle="Create a new Payment" />

        <Formik
          onsubmit={handleSubmitPayments}
          initialValues={initialValues}
          validationSchema={paymentsSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

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
          // getRowId={(row) => `${row.orderLineNumber}`}
          />
        </Box>
      </Box>

    </Box>
  )

}

export default FormAddPayments;
