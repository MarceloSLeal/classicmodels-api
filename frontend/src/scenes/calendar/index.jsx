import { useState, useEffect, useRef } from "react";

import FullCallendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import multiMonthPlugin from '@fullcalendar/multimonth'
import {
  Box, List, ListItem, ListItemText, Typography, useTheme
} from "@mui/material";
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';

import Header from "../../components/Header";
import { tokens } from "../../theme";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import OperationStatusDialog from "../../components/dialogs/OperationStatusDialog";
import useDeleteScenes from '../../components/formsRequests/DeleteScenes';
import usePostForms from "../../components/formsRequests/PostForms";

const Calendar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [currentEvents, setCurrentEvents] = useState([]);
  const urlData = Urls();
  const { data } = useFetchData(urlData.calendar.findAll_Post);
  const [responseCode, setResponseCode] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [status, setStatus] = useState('');
  const calendarRef = useRef(null);
  const { fetchDelete } = useDeleteScenes();
  const { fetchPost } = usePostForms();

  const timeFormat = {
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  }

  const calendarViews = [
    "dayGridMonth",
    "timeGridWeek",
    "timeGridDay",
    "listMonth",
    "multiMonthYear",
  ];

  const views = Object.fromEntries(
    calendarViews.map((view) => [view, { eventTimeFormat: timeFormat, slotLabelFormat: timeFormat }])
  );

  const formatEventTime = (start, end, timeFormat) => {
    const startDate = new Date(start);
    const endDate = new Date(end);

    const sameDay =
      startDate.getFullYear() === endDate.getFullYear() &&
      startDate.getMonth() === endDate.getMonth() &&
      startDate.getDate() === endDate.getDate();

    const startStr = formatDate(startDate, {
      year: "numeric",
      month: "short",
      day: "numeric",
      ...timeFormat,
    });

    const endStr = formatDate(endDate, sameDay ? timeFormat : {
      year: "numeric",
      month: "short",
      day: "numeric",
      ...timeFormat,
    });

    return `${startStr} | ${endStr}`;
  };

  const formatDate = (date) => {
    // Para garantir o offset local (ex: -03:00), usamos Intl API
    const pad = (n) => (n < 10 ? '0' + n : n);

    return date.getFullYear() +
      '-' + pad(date.getMonth() + 1) +
      '-' + pad(date.getDate()) +
      ' ' + pad(date.getHours()) +
      ':' + pad(date.getMinutes())
  };

  const handleDateClick = (selected) => {
    const title = prompt("Please enter a new title for your event");
    const calendarApi = selected.view.calendar;
    calendarApi.unselect();

    if (title) {

      let start = selected.start;
      let end = selected.end;

      if (selected.allDay) {
        const startDate = new Date(start);
        startDate.setHours(6, 0, 0, 0);

        const endDate = new Date(start);
        endDate.setHours(20, 0, 0, 0);

        start = startDate;
        end = endDate;
      }

      const uuid = crypto.randomUUID();

      const eventData = {
        id: uuid,
        title,
        start: formatDatePost(start),
        end: formatDatePost(end),
        allDay: false,
      };

      handleEventPost(eventData).then((status) => {
        console.log("status", status);
        if (status) {
          calendarApi.addEvent(eventData);
        }
      });
    }
  };

  const formatDatePost = (date) => {
    dayjs.extend(utc);
    return dayjs(date).utc().format("YYYY-MM-DDTHH:mm:ss[Z]");
  };


  const handleEventDelete = async (selected) => {
    if (
      window.confirm(
        `Are you sure you want to delete the event '${selected.event.title}'`
      )
    ) {

      try {

        const urlDelete = Urls(selected.event.id);

        const response = await fetchDelete(urlDelete.calendar.put_Delete);

        setResponseCode(response.status);

        if (response.status !== 204) {
          setStatus(`${response.status || 'Failed to delete event'}`);
          setDialogOpen(true);
          return;
        }

        selected.event.remove();

      } catch (error) {
        setStatus("Error: Failed to delete event");
        setResponseCode(error);

        setDialogOpen(true);
      }

    }
  };

  const handleEventPost = async (values) => {
    try {

      const response = await fetchPost(values, urlData.calendar.findAll_Post);

      if (response.status === 'CONNECTION_REFUSED') {
        setStatus(`Error: ${response.status}`);
        setDialogOpen(true);
        return false;
      }

      if (response.ok) {
        return true;
      }

      setResponseCode(response.status);

      if (response.status !== 201) {
        setStatus(`Error: ${response.status || 'Failed to create event'} - ${response.statusText || ''}`);
        setDialogOpen(true);
        return false;
      }

    } catch (error) {
      setStatus(`Error: ${error || 'Failed to create event'}`);
      setDialogOpen(true);
      return false;
    }
  }

  const handleSelectEvent = (id) => {
    const calendarApi = calendarRef.current.getApi();
    const event = calendarApi.getEventById(id);

    if (event) {
      calendarApi.gotoDate(event.start);
    }
  }


  const handleClose = () => {
    setDialogOpen(false);
  }

  useEffect(() => {
    if (data) {
    }
  }, [data]);

  return <Box m="20px">
    <Header title="CALENDAR" subtitle="Full calendar Interactive Page" />

    <Box display="flex" justifyContent="space-between">
      {/* CALENDAR SIDEBAR */}
      <Box
        flex="1 1 20%"
        backgroundColor={colors.primary[400]}
        p="15px"
        borderRadius="4px"
      >
        <Typography variant="h5">Events</Typography>
        <List>
          {currentEvents.map((event) => (
            <ListItem
              key={event.id}
              sx={{
                backgroundColor: colors.greenAccent[500],
                margin: "10px 0",
                borderRadius: "2px",
              }}
              onClick={() => {
                handleSelectEvent(event.id);
              }}
            >
              <ListItemText
                primary={event.title}
                secondary={
                  <Typography>
                    {formatEventTime(event.start, event.end, timeFormat)}
                  </Typography>
                }
              />
            </ListItem>
          ))}
        </List>
      </Box>

      {/* CALENDAR */}
      <Box flex="1 1 100%" ml="15px">
        <FullCallendar
          slotMinTime="06:00:00"
          slotMaxTime="20:00:00"
          height="75vh"
          plugins={[
            dayGridPlugin,
            timeGridPlugin,
            interactionPlugin,
            listPlugin,
            multiMonthPlugin
          ]}
          headerToolbar={{
            left: "prev,next,today",
            center: "title",
            right: "multiMonthYear,dayGridMonth,timeGridWeek,timeGridDay,listMonth"
          }}
          initialView="dayGridMonth"
          editable={true}
          selectable={true}
          selectMirror={true}
          dayMaxEvents={true}
          select={handleDateClick}
          eventClick={handleEventDelete}
          eventsSet={(events) => setCurrentEvents(events)}
          events={data || []}

          eventTimeFormat={timeFormat}
          views={views}
          ref={calendarRef}
        />

        <style>
          {`
            .fc-multimonth .fc-daygrid-day-number {
              color: ${colors.grey[600]};
            }

            .fc-multimonth .fc-day-today .fc-daygrid-day-number {
              color: #d32f2f;
            }
          `}
        </style>

      </Box>
    </Box>

    <OperationStatusDialog
      dialogOpen={dialogOpen} onClose={handleClose} status={status}
      responseCode={responseCode} onClick={handleClose}
    />

  </Box>
};

export default Calendar;
