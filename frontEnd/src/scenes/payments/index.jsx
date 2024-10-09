import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { Box, useTheme } from "@mui/material";
import { styled } from '@mui/material/styles';
import dayjs from 'dayjs';

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
  const navigateEdit = useNavigate();

  // TODO -- formatar a data no dataGrid
  // TODO -- mudar retorno do metodo listar em payments controller, retornar um representation model
  const columns = [
    { field: "orderId", headerName: "ORDER ID", flex: 1 },
    { field: "checkNumber", headerName: "CHECK NUMBER", flex: 1 },
    {
      field: "paymentDate",
      headerName: "PAYMENT DATE",
      flex: 1,
      renderCell: (params) => {
        // Formata a data usando dayjs
        return dayjs(params.value).isValid() ? dayjs(params.value).format('DD/MM/YYYY HH:mm:ss') : "Invalid Date";
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
        // path={"/formaddorders"}
        // TODO -- criar página formaddpayments
        rowId={"checkNumber"}
      />

    </Box>
  )

}

export default Payments;
