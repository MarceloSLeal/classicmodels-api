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
import OrdersAddFormInputs from "../../components/formInputs/OrdersAdd";
import OrdersDetailsFormInputs from "../../components/formInputs/OrdersDetails";
import dayjs from 'dayjs';
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog"

const initialValues = {
  orderId: "",
  paymentDate: dayjs(),
  amount: 0,
};

const paymentsSchema = yup.object.shape({
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

  const [dataOrderIdStatus, setDataOrderIdStatus] = useState(null);
  // TODO -- criar um endpoint para esse select
  FormListCalls(url.products.findByIdNameQuantityInStock, setDataOrderIdStatus);

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

  return (
    <Box>
      <Box m="20px">
        <Header title="CREATE PAYMENT" />

        <Formik
          onsubmit={handleSubmitPayments}
          initialValues={initialValues}
          validationSchema={paymentsSchema}
        >
          {({ values, errors, touched, handleBlur, handleChange, handleSubmit, setFieldValue }) => {

            <form onSubmit={handleSubmit}>
              <Box
                display="grid"
                gap="20px"
                gridTemplateColumns="repeat(5, minmax(0, 1fr))"
                sx={{
                  "& > div": { gridColumn: isNonMobile ? undefined : "span 5" },
                }}
              >

                <Divider sx={{ gridColumn: "span 5" }} />

                {/* TODO -- criar o FormInputs para Payments */}
                <PaymentsFormInputs
                />

              </Box>
            </form>
          }}

        </Formik>
      </Box>
    </Box>
  )

}

export default FormAddPayments;
