package com.pracownia.vanet.model.network;

import com.google.common.collect.Iterables;
import com.pracownia.vanet.model.devices.Device;

import java.util.*;
import java.util.stream.Collectors;

public class Network {
    Map<Device, NetworkNode> networkNodeByDevice;

    public Network(Map<Device, NetworkNode> networkNodeByDevice) {
        this.networkNodeByDevice = networkNodeByDevice;
    }

    public Optional<ConnectionRoute> getRoute(Device from, Device to) {
        if(!networkNodeByDevice.containsKey(from) || !networkNodeByDevice.containsKey(to)){
            return Optional.empty();
        }
        Optional<ImmutableRout> route = search(networkNodeByDevice.get(from), networkNodeByDevice.get(to));
        return route.map(r -> r.nodes.stream().map(NetworkNode::device).collect(Collectors.toList()))
                    .map(nodes -> new ConnectionRoute(nodes.subList(0, nodes.size()-1), to));
    }

    public Set<Device> getConnectedDevices(Device device) {
        if (!networkNodeByDevice.containsKey(device)) {
            return Collections.emptySet();
        }
        return networkNodeByDevice.get(device)
                                  .connectedNodes()
                                  .stream()
                                  .map(NetworkNode::device)
                                  .collect(Collectors.toSet());
    }

    private Optional<ImmutableRout> search(NetworkNode startNode, NetworkNode endpoint) {
        Set<NetworkNode> alreadyVisited = new HashSet<>();
        Queue<ImmutableRout> queue = new ArrayDeque<>();
        queue.add(new ImmutableRout(startNode));

        while (!queue.isEmpty()) {
            ImmutableRout currentRoute = queue.remove();
            NetworkNode currentNode = currentRoute.getLast();
            if (currentNode.equals(endpoint)) {
                return Optional.of(currentRoute);
            } else {
                alreadyVisited.add(currentNode);
                queue.addAll(currentNode.connectedNodes.stream()
                                                       .map(currentRoute::add)
                                                       .collect(Collectors.toList()));
                queue.removeIf((route) -> alreadyVisited.contains(route.getLast()));
            }
        }
        return Optional.empty();
    }

    private static class ImmutableRout {
        private List<NetworkNode> nodes;

        public ImmutableRout(List<NetworkNode> nodes) {
            this.nodes = nodes;
        }

        public ImmutableRout(NetworkNode startNode) {
            this(new ArrayList<>());
            nodes.add(startNode);
        }


        public ImmutableRout add(NetworkNode node){
            ArrayList<NetworkNode> copy = new ArrayList<>(nodes);
            copy.add(node);
            return new ImmutableRout(copy);
        }

        public NetworkNode getLast() {
            return Iterables.getLast(nodes);
        }

        public List<NetworkNode> nodes() {
            return new ArrayList<>(nodes);
        }
    }

}

