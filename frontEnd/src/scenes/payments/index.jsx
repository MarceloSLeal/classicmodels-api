import React, { useState, useEffect } from 'react';

import { Box, useTheme } from "@mui/material";

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";
import BoxDataGrid from "../../components/boxes/BoxDataGrid"

const Payments = () => {
  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.payments.findAll_Post);
  const [rows, setRows] = useState([]);

  const columns = [
    { field: "orderId", headerName: "ORDER ID", flex: 1 },
    { field: "checkNumber", headerName: "CHECK NUMBER", flex: 1, cellClassName: "name-column--cell" },
    {
      field: "paymentDate",
      headerName: "PAYMENT DATE",
      flex: 1,
      renderCell: (params) => {
        const date = new Date(params.value);
        return date instanceof Date && !isNaN(date) ? date.toLocaleString() : "Invalid Date";
      },
    },
    { field: "amount", headerName: "AMOUNT", flex: 1 },
  ]

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="PAYMENTS" subtitle="Manage Payments" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>
    )
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="Payments" subtitle="Manage Payments" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="PAYMENTS" subtitle="Manage Payments" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddpayments"}
        rowId={"checkNumber"}
      />

    </Box>
  )

}

export default Payments;
