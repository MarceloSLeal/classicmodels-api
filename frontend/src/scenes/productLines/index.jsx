import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";

import { GridActionsCellItem } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from "../../components/Header";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import { tokens } from "../../theme";
import ConfirmDeleteDialog from "../../components/dialogs/ConfirmDeleteDialog"
import BoxDataGrid from "../../components/boxes/BoxDataGrid"
import useDeleteScenes from "../../components/formsRequests/DeleteScenes";

const ProductLines = () => {
  const urlData = Urls();
  const theme = useTheme();
  const { data, loading, error, refetchData } = useFetchData(urlData.productlines.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);
  const [idDelete, setIdDelete] = useState(null);
  const colors = tokens(theme.palette.mode);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();
  const { err, fetchDelete } = useDeleteScenes();

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditproductlines", { state: { rowData } });
  };
  const handleDeleteDatagridButton = (params) => () => {
    setIdDelete(params.id);

    setDialogConfirmOpen(true);
  }

  const handleCloseConfirm = () => {
    setDialogConfirmOpen(false);
  }

  const handleDeleteConfirm = async () => {
    setDialogConfirmOpen(false);
    const urlDelete = Urls(idDelete);

    setStatus('');

    try {
      const response = await fetchDelete(urlDelete.productlines.findByProductLine_Put_Delete);

      setStatus(response.status);

      if (response.status === 200) {
        setStatus(`${response.status} Product Line Deleted Succesfully!`);

        refetchData();
      } else {
        setStatus(`Error: ${response.status} Failed to delete Product Line`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message || 'Failed to delete Procut Line'} ${err}`);
    }

    setDialogDeleteOpen(true);
  }

  const handleCloseDelete = () => {
    setDialogDeleteOpen(false);
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
    { field: "productLine", headerName: "PRODUCT LINE", flex: 0.5, cellClassName: "name-column--cell" },
    {
      field: "textDescription",
      headerName: "TEXT DESCRIPTION",
      flex: 1.5,
      minWidth: 150,
      renderCell: (params) => (
        <div
          style={{
            maxHeight: 200,
            overflowY: 'auto',
            whiteSpace: 'normal',
            wordWrap: 'break-word',
            lineHeight: 2,
          }}
        >
          {params.value}
        </div>
      ),
    },
    { field: "htmlDescription", headerName: "HTML DESCRIPTION", flex: 1 },
    {
      field: "image",
      headerName: "IMAGE",
      flex: 1,
      renderCell: (params) => {

        if (!params.value) return null;

        const fileExtension = params.row.image.split('.').pop().toLowerCase();

        const mimeType = {
          jpeg: "image/jpeg",
          png: "image/png",
        }[fileExtension] || "image/jpg";

        // const imageSrc = `data:image/jpg;base64,${params.value}`;
        const imageSrc = `data:${mimeType};base64,${params.value}`;

        return (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              height: "100%",
              width: "100%",
              overflow: "hidden",
            }}
          >
            <img
              src={imageSrc}
              alt="Product Line"
              style={{
                maxHeight: "100%",
                maxWidth: "100%",
                objectFit: "contain",
              }}
            />
          </div>
        );
      },
    },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Edit this Product Line"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Product Line"
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

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="PRODUCT LINES" subtitle="Manage Product Lines" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="PRODUCT LINES" subtitle="Manage Product Lines" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="PRODUCT LINES" subtitle="Manage Product Lines" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddproductlines"}
        rowId={"productLine"}
        rowHeight={200}
      />

      <ConfirmDeleteDialog
        dialogConfirmOpen={dialogConfirmOpen} handleCloseConfirm={handleCloseConfirm}
        contextText={"ID:"} contextVar={idDelete} handleDeleteConfirm={handleDeleteConfirm}
        dialogDeleteOpen={dialogDeleteOpen} handleCloseDelete={handleCloseDelete}
        status={status}
      />

    </Box>
  )
}

export default ProductLines;
