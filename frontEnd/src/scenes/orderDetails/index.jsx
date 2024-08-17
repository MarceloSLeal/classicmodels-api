import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { DataGrid, GridActionsCellItem, GridToolbarContainer } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import Button from "@mui/material/Button";
import ViewListOutlinedIcon from '@mui/icons-material/ViewListOutlined';

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
  const navigate = useNavigate();
  const navigateSelectOrder = useNavigate();

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

  // TODO -- Decidir o que fazer nesse botão ou se colocar mais alguns ou nenhum
  const EditToolBar = () => {
    const handleClick = () => {
      navigate("/orderdetails")
    }

    return (
      <GridToolbarContainer>
        <Button
          sx={{
            backgroundColor: colors.blueAccent[700],
            color: colors.primary[100],
            paddingTop: '10px', paddingRight: '10px',
            paddingBottom: '10px', paddingLeft: '10px',
          }}
          startIcon={<AddIcon />} onClick={handleClick}>
          ???
        </Button>
      </GridToolbarContainer>
    );
  }

  const handleSelectOrderId = (params) => {
    const rowData = params.row;
    navigateSelectOrder("/selectorderid", { state: { rowData } });
  }

  const columns = [

    { field: "orderId", headerName: "ORDER ID", flex: 0.5 },
    { field: "productId", headerName: "PRODUCT ID", flex: 1 },
    { field: "quantityOrdered", headerName: "QUANTITY ORDERED", flex: 1 },
    { field: "priceEach", headerName: "PRICE EACH", flex: 1 },
    { field: "orderLineNumber", headerName: "ORDER LINE NUMBER", flex: 1 },
    {
      field: 'actions',
      type: 'actions',
      headerName: 'SELECT ORDER',
      width: 100,
      cellClassName: 'actions',
      flex: 0.5,
      getActions: (params) => {

        return [
          <GridActionsCellItem
            icon={<ViewListOutlinedIcon />}
            label="SELECT ORDER ID"
            className="textPrimary"
            onClick={handleSelectOrderId(params)}
            color="inherit"
          />,
        ]
      }
    },
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
          getRowId={(row) => `${row.orderId}-${row.productId}`}
          slots={{
            // TODO -- se não for usar o botao no cabeçalho remover slots
            toolbar: EditToolBar,
          }}
        />
      </Box>
    </Box>
  )

}

export default OrderDetails;
