import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { DataGrid, GridActionsCellItem } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import ViewListOutlinedIcon from '@mui/icons-material/ViewListOutlined';
import ToysOutlinedIcon from '@mui/icons-material/ToysOutlined';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";

const OrderDetails = () => {

  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.orderdetails.findAll);
  const [rows, setRows] = useState([]);
  const navigateSelectOrder = useNavigate();
  const navigateSelectProduct = useNavigate();

  const handleSelectOrderId = (params) => {
    const rowData = params.row;
    navigateSelectOrder("/selectorderid", { state: { rowData } });
  }

  const handleSelectProductId = (params) => {
    const rowData = params.row;
    navigateSelectProduct("/selectproductid", { state: { rowData } });
  }

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
    {
      field: 'selectOrder',
      type: 'actions',
      headerName: 'SELECT ORDER',
      width: 100,
      cellClassName: 'actions',
      flex: 0.8,
      getActions: (params) => {

        return [
          <GridActionTooltip title="select this order separately on another page"
            placement="bottom">
            <GridActionsCellItem
              icon={<ViewListOutlinedIcon />}
              label="SELECT ORDER ID"
              className="textPrimary"
              onClick={() => handleSelectOrderId(params)}
              color="inherit"
            />
          </GridActionTooltip>,
        ]
      }
    },

    { field: "orderId", headerName: "ORDER ID", flex: 0.5 },

    {
      field: 'selectProduct',
      type: 'actions',
      headerName: 'SELECT PRODUCT',
      width: 100,
      cellClassName: 'actions',
      flex: 0.8,
      getActions: (params) => {

        return [

          <GridActionTooltip title="select all orders in which this product was purchased"
            placement="bottom">

            <GridActionsCellItem
              icon={<ToysOutlinedIcon />}
              label="SELECT PRODUCT ID"
              className="textPrimary"
              onClick={() => handleSelectProductId(params)}
              color="inherit"
            />
          </GridActionTooltip>,
        ]
      }
    },

    { field: "productId", headerName: "PRODUCT ID", flex: 1 },
    { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 1 },
    { field: "priceEach", headerName: "PRICE EACH", flex: 1 },
    { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
  ]

  useEffect(() => {
    if (data) {
      const sortedData = data.sort((a, b) => {
        if (a.orderId !== b.orderId) {
          return a.orderId - b.orderId;
        }
        return a.orderLineNumber - b.orderLineNumber;
      });
      setRows(sortedData);
    }
  }, [data]);

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
            color: `${colors.grey[100]}!important`,
          },
        }}
      >
        <DataGrid
          rows={rows}
          columns={columns}
          getRowId={(row) => `${row.orderId} - ${row.productId}`}
        />
      </Box>

    </Box>
  )

}

export default OrderDetails;
