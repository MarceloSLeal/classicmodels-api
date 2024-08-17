import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';

import { DataGrid } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";

const SelectOrderId = () => {
  const location = useLocation();
  const { rowData } = location.state || {};

  const url = Urls(rowData.orderId);
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.orderdetails.findByOrderId);
  const [rows, setRows] = useState([]);

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  const columns = [
    { field: "orderId", headerName: "ORDER ID", flex: 0.5 },
    { field: "productId", headerName: "PRODUCT ID", flex: 1 },
    { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 1 },
    { field: "priceEach", headerName: "PRICE EACH", flex: 1 },
    { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
  ]

  if (loading) {
    return (
      <Box m="20px">
        <Header title="ORDER DETAILS" subtitle="Show Order Details" />
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
        <Header title="ORDER DETAILS" subtitle="Show Order Details" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="ORDER DETAILS" subtitle="Show Order Details" />
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
          // TODO -- depois colocar o orderLineNumber como RowId
          getRowId={(row) => `${row.orderId}-${row.productId}`}
        />
      </Box>
    </Box>
  )

}

export default SelectOrderId;
